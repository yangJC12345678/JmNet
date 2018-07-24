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
package org.jmin.net;

import org.jmin.net.event.PipeConnectedEvent;
import org.jmin.net.event.PipeDataReadEvent;
import org.jmin.net.event.PipeDisconnectedEvent;
import org.jmin.net.event.PipeErrorEvent;

/**
 * 网络连接器上的事件监听器
 * 
 * @author Chris Liao 
 */
public interface PipeListener {
	
  /**
   * When connection request coming,this method will be called
   */
  public void onConnected(PipeConnectedEvent event);
  
  /**
   * Method run after remote host close connection.
   */
  public void onDisconnected(PipeDisconnectedEvent event);
	
  /**
   * Read data from remote host
   */
  public void onDataReadOut(PipeDataReadEvent event);
  
  /**
   * When errors occur during communication
   */
  public void onError(PipeErrorEvent event);

}
