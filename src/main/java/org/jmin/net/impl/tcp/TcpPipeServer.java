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
package org.jmin.net.impl.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.jmin.net.PipeListener;
import org.jmin.net.PipeServerListener;
import org.jmin.net.event.PipeConnectEvent;
import org.jmin.net.event.PipeConnectedEvent;
import org.jmin.net.event.PipeDisconnectedEvent;
import org.jmin.net.event.PipeErrorEvent;
import org.jmin.net.event.ServerClosedEvent;
import org.jmin.net.event.ServerCreatedEvent;
import org.jmin.net.impl.BasePipeServer;
import org.jmin.net.impl.util.PipeUtil;

/**
 * TCP server定义
 * 
 * @author Chris Liao 
 */
public class TcpPipeServer extends BasePipeServer {
	
	/**
	 * Server socket
	 */
	private ServerSocket serverSocket;
	
	/**
	 * server监听线程
	 */
	private TcpPipeServerThread pipeServerThread;
	
	/**
	 * 构造函数
	 */
	public TcpPipeServer(int serverPort,PipeServerListener pipeServerListener,PipeListener pipeListener)throws IOException {
		super(serverPort,pipeServerListener,pipeListener);
		this.serverSocket = new ServerSocket(serverPort);
		this.pipeServerThread = new TcpPipeServerThread(this);
	  this.pipeServerThread.setDaemon(true);
	  this.handleEvent(new ServerCreatedEvent(serverPort));
	}

	/**
	 * 服务端Socket
	 */
	public ServerSocket getServerSocket(){
		return serverSocket;
	}
	
	/**
	 * 是否已经关闭
	 */
	public boolean isClosed(){
		return this.serverSocket.isClosed();
	}
	
  /**
   * 是否处于运行状态
   */
  public boolean isListening(){
  	return this.pipeServerThread.isAlive();
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
		throw new SocketException("Server has been closed");
	 }else{
   	PipeUtil.close(serverSocket);
		this.pipeServerThread.interrupt();
		this.handleEvent(new ServerClosedEvent(serverSocket.getLocalPort())); 
	 }
	}
	
	/**
	 * Socket server under IO mode
	 *
	 * @author Chris
	 */
	private class TcpPipeServerThread extends Thread{
		
  	/**
  	 * 父线程
  	 */
  	private Thread parentThread;
		
		/**
		 * 当前Thread需要监听的socketServer
		 */
		private TcpPipeServer pipeServer;
		
	  /**
	   * 构造函数
	   */
	  public TcpPipeServerThread(TcpPipeServer pipeServer){
	  	this.pipeServer=pipeServer;
	  	this.parentThread=Thread.currentThread();
	  }

	  /**
	   * Thread method
	   */
	  public void run(){
	  	TcpPipe pipe = null;
	  	ServerSocket serverSocket = pipeServer.getServerSocket();
	    while(this.parentThread.isAlive() && !this.pipeServer.isClosed()){
	      try{
	        Socket socket = serverSocket.accept();
	        pipe = new TcpPipe(socket,pipeServer.getPipeListener(),pipeServer);
          pipe.setReadBuffSize(pipeServer.getReadBuffSize());
          pipe.setWriteBuffSize(pipeServer.getWriteBuffSize());
	        pipe.handleEvent(new PipeConnectEvent(pipe));
	        if(!pipe.isClosed()){//管道没有被关闭的情况下，发布已连接事件，并让其处于运行
	         pipe.handleEvent(new PipeConnectedEvent(pipe));
		       pipe.keepListening();
	        }
	      }catch(Throwable e){
	      	if(pipe!=null){
		    		pipe.handleEvent(new PipeErrorEvent(pipe,e));
		        pipe.handleEvent(new PipeDisconnectedEvent(pipe));
		       	PipeUtil.close(pipe);
	      	}
	      } 
	    }
	  }
	}
}
