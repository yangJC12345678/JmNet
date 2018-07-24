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
package org.jmin.net.event;

import java.util.EventObject;

import org.jmin.net.Pipe;
import org.jmin.net.PipeAddress;

/**
 * 发生在网络连接器上的事件
 * 
 * @author Chris Liao 
 */
public class PipeEvent extends EventObject {
	
	/**
	 * 构造函数
	 */
  public PipeEvent(Pipe socket) {
    super(socket);
  }
  
  /**
   * 获得远程地址
   */
  public PipeAddress getRemoteHost(){
  	return ((Pipe)super.getSource()).getRemoteHost();
  }
}