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
package org.jmin.net.impl;

import java.io.IOException;
import java.util.EventObject;

import org.jmin.net.Pipe;
import org.jmin.net.PipeListener;
import org.jmin.net.event.PipeConnectedEvent;
import org.jmin.net.event.PipeDataReadEvent;
import org.jmin.net.event.PipeDisconnectedEvent;
import org.jmin.net.event.PipeErrorEvent;

/**
 * 通讯管道
 * 
 * @author Chris Liao 
 */
public abstract class BasePipe implements Pipe {
  
	/**
	 * 是否处于serverside
	 */
	private boolean isServerSide;
	
	/**
	 * 服务器
	 */
	private BasePipeServer pipeServer;

	/**
	 * 事件监听
	 */
	private PipeListener pipeListener;
	
	/**
	 * 默认读取消息的超时:10分钟
	 */
	private final int DefaultTimeout = 180000;

	/**
	 * 构造函数
	 */
	public BasePipe(PipeListener pipeListener){
	 this.isServerSide=false;
	 this.pipeListener=pipeListener;
	}

	/**
	 * 构造函数
	 */
	public BasePipe(PipeListener pipeListener,BasePipeServer pipeServer){
		if(pipeServer!=null)
	  this.isServerSide=true;
		this.pipeServer=pipeServer;
		this.pipeListener=pipeListener;
	}
	
	/**
	 * 是否位于服务器端
	 */
	public boolean isServerSide(){
		return this.isServerSide;
	}
	
	/**
	 * 服务器
	 */
	public BasePipeServer getPipeServer(){
		return this.pipeServer;
	}
  
	/**
	 * 将数据通过连接输送给远程连接方
	 */
	public synchronized byte[] read()throws IOException{
		throw new IOException("Not Implemented");
	}

	/**
	 * 将数据通过连接输送给远程连接方
	 */
	public synchronized byte[] read(int timeout)throws IOException{
		throw new IOException("Not Implemented");
	}
	
	/**
	 * 将数据同步写给对方，并需要对方给出回复
	 */
	public synchronized byte[] writeSyn(byte[] data)throws IOException{
		return this.writeSyn(data,DefaultTimeout);
	}
	
	/**
	 * 将数据同步写给对方，并需要对方给出回复
	 */
	public synchronized byte[] writeSyn(byte[] data,int timeout)throws IOException{
		this.write(data);
		return this.read(timeout);
	}
	
  /**
   * override method
   */
  public String toString() {
    return this.getRemoteHost().toString();
  }
  
  /**
   * Implementation method from observer interface.
   */
	public void handleEvent(EventObject evnet) {
   try {
  	 if(pipeListener !=null){
	      if(evnet instanceof PipeConnectedEvent){
	    	  pipeListener.onConnected((PipeConnectedEvent)evnet);
	    	}else if(evnet instanceof PipeDisconnectedEvent){
	    	  pipeListener.onDisconnected((PipeDisconnectedEvent)evnet);
	    	}else if(evnet instanceof PipeDataReadEvent) {
	        pipeListener.onDataReadOut((PipeDataReadEvent)evnet);
	      }else if (evnet instanceof PipeErrorEvent) {
	        pipeListener.onError((PipeErrorEvent)evnet);
	      }
  	  }
    }catch (Throwable e) {
     e.printStackTrace();
    }
    
	  try{
		 if(pipeServer!=null)
		  pipeServer.handleEvent(evnet);
	  }catch (Throwable e) {
	   e.printStackTrace();
	  }
  }
}