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

import java.io.IOException;

/**
 * 网络连接管道
 *
 * @author Chris Liao
 */
public interface Pipe {
	
  /**
   * 是否关闭
   */
  public boolean isClosed();
  
  /**
   * 是否处于监听状态
   */
  public boolean isListening();
  
  /**
   * 返回本地地址
   */
  public PipeAddress getLocalHost();

  /**
   * 返回远程地址
   */
  public PipeAddress getRemoteHost();
  
  /**
   * 设置读缓存区间大小
   */
  public void setReadBuffSize(int buffSize)throws IOException;

  /**
   * 设置写缓存区间大小
   */
  public void setWriteBuffSize(int buffSize)throws IOException;
  
  /**
   * 关闭连接
   */
  public void close()throws IOException;
 
  /**
   * 保持监听
   */
  public void keepListening()throws IOException;

  /**
   * 从连接上读出远程发送过来的数据
   */
  public byte[] read() throws IOException;

  /**
   * 将数据通过连接输送给远程连接方
   */
  public void write(byte[] data) throws IOException;
  
  /**
   * 将数据同步写给对方，并需要对方给出回复,默认超过时间为一小时
   */
  public byte[] writeSyn(byte[] data) throws IOException;
}
