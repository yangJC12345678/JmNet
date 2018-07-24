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

import org.jmin.net.PipeListener;
import org.jmin.net.PipeServer;
import org.jmin.net.PipeServerListener;
import org.jmin.net.event.ServerClosedEvent;
import org.jmin.net.event.ServerCreatedEvent;
import org.jmin.net.event.ServerErrorEvent;

/**
 * 通讯Server
 * 
 * @author Chris Liao 
 */
public abstract class BasePipeServer implements PipeServer {
	
	/**
	 * 服务器端口
	 */
	private int serverPort;
	
	/**
	 * 读缓存大小
	 */
	private int readBuffSize=1024;
	
	/**
	 * 写缓存大小
	 */
	private int writeBuffSize=1024;

	/**
	 * Socket事件处理者
	 */
	private PipeListener pipeListener;

  /**
   * Server事件处理者
   */
	private PipeServerListener pipeServerListener;
	
	/**
	 * 构造函数
	 */
	public BasePipeServer(int serverPort,PipeServerListener pipeServerListener,PipeListener pipeListener){
		this.serverPort=serverPort;
		this.pipeListener=pipeListener;
		this.pipeServerListener=pipeServerListener;
	}
	
	/**
	 * 获得服务器端口
	 */
	public int getServerPort(){
		return this.serverPort;
	}
	
	/**
	 * Socket事件处理者
	 */
	public PipeListener getPipeListener() {
		return pipeListener;
	}
	
	/**
	 * Server事件处理者
	 */
	public PipeServerListener getPipeServerListener() {
		return pipeServerListener;
	}
	
	/**
   * 获取读缓存区间大小
   */
	public int getReadBuffSize() {
		return readBuffSize;
	}
	
	/**
   * 获取写缓存区间大小
   */
	public int getWriteBuffSize() {
		return writeBuffSize;
	}
	
  /**
   * 设置读缓存区间大小
   */
  public void setReadBuffSize(int buffSize)throws IOException{
   if(buffSize <= 0)throw new IllegalArgumentException("Invalid read buffer size");
   this.readBuffSize = buffSize;
  }

  /**
   * 设置写缓存区间大小
   */
  public void setWriteBuffSize(int buffSize)throws IOException{
   	if(buffSize <= 0)throw new IllegalArgumentException("Invalid write buffer size");
  	this.writeBuffSize = buffSize;
  }

	/**
	 * Implementation method from observer interface.
	 */
  public void handleEvent(EventObject arg) {
		try{
			if(pipeServerListener!=null){
				if(arg instanceof ServerCreatedEvent) {
					pipeServerListener.onCreated((ServerCreatedEvent)arg);
				}else if (arg instanceof ServerClosedEvent) {
					pipeServerListener.onClosed((ServerClosedEvent)arg);
				}else if (arg instanceof ServerErrorEvent) {
					pipeServerListener.onError((ServerErrorEvent)arg);
				}
			}
		}catch(Throwable e){
		  e.printStackTrace();
		}
	}
}
