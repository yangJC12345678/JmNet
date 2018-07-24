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
package org.jmin.test.net;

import org.jmin.log.LogPrinter;
import org.jmin.net.PipeListener;
import org.jmin.net.PipeServerListener;
import org.jmin.net.event.PipeConnectEvent;
import org.jmin.net.event.PipeConnectedEvent;
import org.jmin.net.event.PipeDataReadEvent;
import org.jmin.net.event.PipeDisconnectedEvent;
import org.jmin.net.event.PipeErrorEvent;
import org.jmin.net.event.ServerClosedEvent;
import org.jmin.net.event.ServerCreatedEvent;
import org.jmin.net.event.ServerErrorEvent;

/**
 * Server的事件监听
 *
 * @author Chris Liao
 */

public class ServerEventListener implements PipeServerListener,PipeListener {

	/**
	 * 日记打印
	 */
	private LogPrinter log = LogPrinter.getLogPrinter(ServerEventListener.class);

	/**
	 *创建Server触发事件
	 */
	public void onCreated(ServerCreatedEvent event) {

		/**
		 * 打印Server的端口
		 */
		log.info("Created server on port: " + event.getPort());
	}

	/**
	 * Server发生错误时触发事件
	 */
	public void onError(ServerErrorEvent event) {

		/**
		 * 打印错误信息
		 */
		log.info("Catched a server exception: " + event.detail);
	}

	/**
	 * 当Server关闭时触发事件
	 */
	public void onClosed(ServerClosedEvent event) {

		/**
		 * print server close event
		 */
		log.info("Closed server on port: " + event.getPort());
	}

	/**
	 *当有远程请求连接的时候,触发事件
	 */
	public void onConnect(PipeConnectEvent event) {

		/**
		 * 打印消息来源
		 */
		log.info("onConnect request pipe: " + event.getRemoteHost());
	}
	
	/**
	 *当有远程请求连接的时候,触发事件
	 */
	public void onConnected(PipeConnectedEvent event) {

		/**
		 * 打印消息来源
		 */
		log.info("onConnected request pipe: " + event.getRemoteHost());
	}
	

	/**
	 * 当有客户端关闭连接时候,触发事件
	 */
	public void onDisconnected(PipeDisconnectedEvent event) {

		/**
		 * 打印客户端的网络地址
		 */
		log.info("Found disconnected pipe: "+event.getRemoteHost()+"]");
	}
	
  /**
   * 当读取到Server发送过来的消息，将会触发这个事件
   */
  public void onDataReadOut(PipeDataReadEvent event) {

    /**
     * 获取发送过来的Byte数据
     */
    byte[] data = event.getReadData();

    /**
     * 打印读取到的数据
     */
    log.info("Read out data [" + new String(data)  + "] from pipe: "+ event.getSource());
    
		/**
		 * 回复给客户端的消息
		 */
		String replyMessage ="Received,thanks";
		
		/**
		 * 设置给远程的回复
		 */
		event.setReplyData(replyMessage.getBytes());
  }
  
  /**
   * 当在网络连接上发生错误的时候，触发该错误。
   */
  public void onError(PipeErrorEvent event) {

    /**
     * 获取异常
     */
    Throwable cause = event.getCause();

    /**
     * 打印异常
     */
    log.error("Catched a exception:", cause);
  }
}