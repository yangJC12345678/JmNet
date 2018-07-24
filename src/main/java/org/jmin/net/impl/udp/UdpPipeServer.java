/*
 * Copyright (C) Chris Liao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jmin.net.impl.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.jmin.net.PipeAddress;
import org.jmin.net.PipeListener;
import org.jmin.net.PipeServerListener;
import org.jmin.net.event.PipeConnectedEvent;
import org.jmin.net.event.PipeDataReadEvent;
import org.jmin.net.event.ServerClosedEvent;
import org.jmin.net.event.ServerCreatedEvent;
import org.jmin.net.impl.BasePipeServer;

/**
 * 为了提高UDP数据传输性,提供了两个端口：一个专门用来读取数据，一个用来专门写数据
 * 
 * @author Chris Liao 
 */
public class UdpPipeServer extends BasePipeServer {
	
	/**
	 *server端的pipe
	 */
	private UdpPipe serverPipe;
	
	/**
	 *读的缓冲区
	 */
	private byte[] dataReadBuff;
	
	/**
	 * 数据读Socket,专门用来读取用户连接请求数据
	 */
	private DatagramSocket dataSocket;

	/**
	 * Server thread
	 */
	private UdpPipeServerThread pipeServerThread;
	
	/**
	 * 构造函数
	 */
	public UdpPipeServer(int port,PipeServerListener pipeServerListener,PipeListener pipeListener)throws IOException{
		super(port,pipeServerListener,pipeListener);
		this.dataSocket = new DatagramSocket(port);
		this.pipeServerThread=new UdpPipeServerThread(this);
	  this.pipeServerThread.setDaemon(true);
	  this.handleEvent(new ServerCreatedEvent(port));
	  this.dataReadBuff = new byte[dataSocket.getReceiveBufferSize()];
  	this.serverPipe = new UdpPipe(dataSocket,pipeListener,this,pipeServerThread);
	}
	
	/**
	 * server端的pipe
	 */
	public UdpPipe getServerPipe(){
		return this.serverPipe;
	}
	
	/**
	 * 数据socket
	 */
	public DatagramSocket getDataSocket(){
		return this.dataSocket;
	}
	
	/**
	 * 读的缓冲区
	 */
	public byte[] getDataReadBuffer(){
		return this.dataReadBuff;
	}
	
	/**
	 * 是否已经关闭
	 */
	public boolean isClosed(){
		return this.dataSocket.isClosed();
	}
	
  /**
   * 是否处于运行状态
   */
  public boolean isListening(){
  	return pipeServerThread.isAlive();
  }
  
  /**
   * 设置读缓存区间大小
   */
  public void setReadBuffSize(int buffSize)throws IOException{
 	 if(isClosed()) throw new SocketException("Server pipe has been closed,forbidden"); 
	 if(this.isListening()) throw new SocketException("Server pipe is in listening,forbidden"); 
   if(buffSize <= 0)throw new IllegalArgumentException("Invalid read buffer size");
   this.dataSocket.setReceiveBufferSize(buffSize);
	 this.dataReadBuff = new byte[dataSocket.getReceiveBufferSize()];
  }

  /**
   * 设置写缓存区间大小
   */
  public void setWriteBuffSize(int buffSize)throws IOException{
  	if(isClosed()) throw new SocketException("Server pipe has been closed,forbidden"); 
  	if(this.isListening()) throw new SocketException("Server pipe is in listening,forbidden"); 
    if(buffSize <= 0)throw new IllegalArgumentException("Invalid write buffer size");
  	this.dataSocket.setSendBufferSize(buffSize);
  }
  
	/**
	 * 让服务器运行起来
	 */
	public void startup()throws IOException{
		if(this.isListening()){
			 throw new SocketException("Server is running");
		}else{
	  	this.pipeServerThread.start();
  	}
	}
	
	/**
	 * 关闭Server
	 */
	public void close()throws IOException{
		if(this.isClosed()){
			throw new SocketException("Server is closed");
		}else{
			this.dataSocket.close();
			this.pipeServerThread.interrupt();
			this.handleEvent(new ServerClosedEvent(this.getServerPort()));
		}
	}
	
	/**
	 * UDP Server线程
	 *
	 * @author Chris
	 */
	private class UdpPipeServerThread extends Thread{
		
		/**
		 * 父线程
		 */
		private Thread parentThread;

		/**
		 * 当前Thread需要监听的socketServer
		 */
		private UdpPipeServer pipeServer;
		
	  /**
	   * server thread
	   */
	  public UdpPipeServerThread(UdpPipeServer pipeServer){
	  	this.pipeServer=pipeServer;
	  	this.parentThread =Thread.currentThread();
	  }
	  
	  /**
	   * Thread method
	   */
	  public void run(){
	  	UdpPipe serverPipe = pipeServer.getServerPipe();
	    byte[] readBuffer = pipeServer.getDataReadBuffer();
	    DatagramSocket dataSocket = pipeServer.getDataSocket();
	  	DatagramPacket packet = new DatagramPacket(readBuffer,readBuffer.length);
	  	while(this.parentThread.isAlive() && !pipeServer.isClosed()){
	  		try{
					dataSocket.receive(packet);
					InetAddress remoteAddr=packet.getAddress();
					PipeAddress remoteHost = new PipeAddress(remoteAddr.getHostAddress(),remoteAddr.getHostName(),packet.getPort());
					serverPipe.setRemoteHost(remoteAddr,remoteHost);
					serverPipe.handleEvent(new PipeConnectedEvent(serverPipe));//因为是无连接，只能每次触发连接事件
					
				  byte[]data = new byte[packet.getLength()];
					System.arraycopy(readBuffer,0,data,0,data.length);
				  PipeDataReadEvent event = new PipeDataReadEvent(serverPipe,data);
				  serverPipe.handleEvent(event);
					byte[]replyData = event.getReplyData();
					if(replyData!=null && replyData.length >0 && !serverPipe.isClosed()){
						serverPipe.write(replyData);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
	    }
	  }
	}
}