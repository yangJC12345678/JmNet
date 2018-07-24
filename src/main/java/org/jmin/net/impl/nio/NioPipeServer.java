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
package org.jmin.net.impl.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
 * Nio Server
 *
 * @author Chris Liao
 */
public class NioPipeServer extends BasePipeServer {

  /**
   * nio selector
   */
  private Selector serverSelector;

  /**
   * serverSocketChannel
   */
  private ServerSocketChannel serverSocketChannel;

  /**
   * 服务器等待接受线程
   */
  private NioPipeServerThread pipeServerThread;

  /**
   * 存放所有已经注册上的蔟
   */
  private List registeredPipeClusterList;
  
  /**
   * task timer
   */
  private Timer pipeClusterClearTimer;
 

  /**
   * 构造函数
   */
  public NioPipeServer(int port,PipeServerListener pipeServerListener,PipeListener pipeListener) throws IOException {
    super(port,pipeServerListener,pipeListener);
    this.serverSocketChannel = ServerSocketChannel.open();
    this.serverSocketChannel.socket().bind(new InetSocketAddress(port));
    this.serverSocketChannel.configureBlocking(false);
    this.serverSelector = Selector.open();
    this.serverSocketChannel.register(serverSelector,SelectionKey.OP_ACCEPT);
    this.pipeServerThread = new NioPipeServerThread(this);
    this.handleEvent(new ServerCreatedEvent(port));
    
    this.registeredPipeClusterList = new ArrayList();
    this.pipeClusterClearTimer = new Timer(true);
    this.pipeClusterClearTimer.schedule(new ClearSelectorTask(this),1000,5000);
  }

	/**
	 * 是否已经关闭
	 */
	public boolean isClosed(){
		return this.serverSocketChannel.socket().isClosed();
	}
	
  /**
   * 是否处于运行状态
   */
  public boolean isListening(){
  	return pipeServerThread.isAlive();
  }
  
  /**
   * return select key
   */
  public Selector getServerSelector() {
    return this.serverSelector;
  }
  
  /**
   * return select key
   */
  public ServerSocketChannel getServerSocketChannel() {
    return this.serverSocketChannel;
  }

	/**
	 * 让服务器运行起来
	 */
	public void startup()throws IOException{
		if(this.isListening()){
			throw new SocketException("Server is running");
		}else{
 		 this.pipeServerThread.setDaemon(true);
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
		  this.serverSocketChannel.close();
		  this.clearEmptyCluster();
			this.pipeServerThread.interrupt();
			this.handleEvent(new ServerClosedEvent(this.getServerPort()));
		}
	}
	
  /**
   * 注册一个连接
   */
  void registerServerPipe(NioPipe pipe)throws IOException{
  	NioPipeCluster targetCluster = null;
  	Iterator itor = registeredPipeClusterList.iterator();
  	while(itor.hasNext()){
  		NioPipeCluster cluster = (NioPipeCluster)itor.next();
      if(!cluster.isFull()) {
        targetCluster = cluster;
        break;
      }
  	}

    if(targetCluster == null) {
      targetCluster = new NioPipeCluster();
      registeredPipeClusterList.add(targetCluster);
      targetCluster.setDaemon(true);
      targetCluster.start();
    }
    
    targetCluster.registerPipe(pipe);
  }

  /**
   * 注册一个连接
   */
  synchronized void clearEmptyCluster(){
  	this.pipeClusterClearTimer.cancel();
    Iterator itor = registeredPipeClusterList.iterator();
    while(itor.hasNext()){
    	NioPipeCluster selector = (NioPipeCluster)itor.next();
      if(selector.isEmpty()){
      	selector.terminate();
        itor.remove();
      }
    }
  }
  
  /**
   * 检查是否存在空的Pipe selector,如果存在则关闭
   */
  private class ClearSelectorTask extends TimerTask{

    /**
     * 连接簇
     */
    private NioPipeServer pipeServer;

    /**
     * 构造函数
     */
    public ClearSelectorTask(NioPipeServer pipeServer){
      this.pipeServer = pipeServer;
    }

    /**
     * 任务方法
     */
    public void run(){
    	pipeServer.clearEmptyCluster();
    }
  }
  
	/**
	 * Socket server thread
	 */
  private class NioPipeServerThread extends Thread {
  	
  	/**
  	 * 父线程
  	 */
  	private Thread parentThread;
  	
		/**
		 * 当前Thread需要监听的Server
		 */
		private NioPipeServer pipeServer;
		
	  /**
	   * 构造函数
	   */
	  public NioPipeServerThread(NioPipeServer pipeServer) {
	   	this.pipeServer=pipeServer;
	  	this.parentThread =Thread.currentThread();
	  }

	  /**
	   * Thread method
	   */
	  public void run() {
	  	NioPipe pipe = null;
	    Selector serverSelector = pipeServer.getServerSelector();
	    ServerSocketChannel serverChannel=pipeServer.getServerSocketChannel();
	    while(this.parentThread.isAlive() && !pipeServer.isClosed()) {
	      try {
	        if(serverSelector.select() >0){
		        Iterator itor = serverSelector.selectedKeys().iterator();
		        while(itor.hasNext()) {
		          SelectionKey key =(SelectionKey) itor.next();
		          itor.remove();
		          if(key.isAcceptable()) {
		            SocketChannel socketChannel = serverChannel.accept();
		            pipe = new NioPipe(socketChannel,pipeServer.getPipeListener(),pipeServer);
		            pipe.setReadBuffSize(pipeServer.getReadBuffSize());
		            pipe.setWriteBuffSize(pipeServer.getWriteBuffSize());

		            pipe.handleEvent(new PipeConnectEvent(pipe));
		            if(!pipe.isClosed()){//管道没有被关闭的情况下，发布已连接事件，并让其处于运行
		             pipe.handleEvent(new PipeConnectedEvent(pipe));
			           pipeServer.registerServerPipe(pipe);
		            }
		          }else if(key.isConnectable()){
	
		          }
		        }
	        }
	      }catch (Throwable e) {
	      	if(pipe!=null){
		      	PipeUtil.close(pipe);
		        pipe.handleEvent(new PipeErrorEvent(pipe,e));
		        pipe.handleEvent(new PipeDisconnectedEvent(pipe));
	      	}
	      }
	    }
	  }
	}
}
