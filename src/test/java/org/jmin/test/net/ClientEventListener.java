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
import org.jmin.net.event.PipeConnectEvent;
import org.jmin.net.event.PipeConnectedEvent;
import org.jmin.net.event.PipeDataReadEvent;
import org.jmin.net.event.PipeDisconnectedEvent;
import org.jmin.net.event.PipeErrorEvent;

/**
 * 客户端 连接事件的监听
 *
 * @author Chris Liao
 */

public class ClientEventListener implements PipeListener {

  /**
   * 日记打印
   */
  private LogPrinter log = LogPrinter.getLogPrinter(ClientEventListener.class);
  
  /**
   * When connection request coming,this method will be called
   */
  public void onConnect(PipeConnectEvent event){
  	log.info("onConnect");
  }

  /**
   * When connection request coming,this method will be called
   */
  public void onConnected(PipeConnectedEvent event){
  	log.info("onConnected");
  }
  
  /**
   * Method run after remote host close connection.
   */
  public void onDisconnected(PipeDisconnectedEvent event){
   	log.info("onDisconnected");
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
    log.info("Read out data [" + new String(data) + "] from pipe: " + event.getSource());

    /**
     * 放置回复数据
     */
    event.setReplyData("Hello,Chris".getBytes());
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
    log.error("Catched a exception:" + cause.getMessage() + "]");
  }
}