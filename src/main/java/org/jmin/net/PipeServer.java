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
 * 网络服务器
 *
 * @author Chris Liao
 */
public interface PipeServer {
	
  /**
   * 是否已经关闭
   */
  public boolean isClosed();
  
  /**
   * 是否处于运行状态
   */
  public boolean isListening();
  
  /**
   * 设置读缓存区间大小
   */
  public void setReadBuffSize(int buffSize)throws IOException;

  /**
   * 设置写缓存区间大小
   */
  public void setWriteBuffSize(int buffSize)throws IOException;
  
  /**
   * 关闭Server
   */
  public void close()throws IOException;

  /**
   * 让Server运行起来，并处于等待接收客户的连接请求的状态
   */
  public void startup() throws IOException;
 
}
