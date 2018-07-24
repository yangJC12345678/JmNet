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

import org.jmin.net.Pipe;

/**
 * 当监听在某个连接上读取到数据触发
 *
 * @author Chris
 */

public class PipeDataReadEvent extends PipeEvent {

  /**
   * receive data
   */
  private byte[] readData;
  
  /**
   * 将回复的数据
   */
  private byte[] replyData;

  /**
   * Constructor with a source object.
   */
  public PipeDataReadEvent(Pipe source, byte[] data) {
    super(source);
    this.readData = data;
  }

  /**
   * Return byte data
   * @return
   */
  public byte[] getReadData() {
    return readData;
  }
  
  /**
   * 获得需要回复的数据
   */
  public byte[] getReplyData(){
  	return this.replyData;
  }
  
  /**
   * 获得需要回复的数据
   */
  public void setReplyData(byte[] replyData){
  	 this.replyData = replyData;
  }
}