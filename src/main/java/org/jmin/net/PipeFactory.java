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
 * 网络协议服对象工厂
 *
 * @author Chris Liao
 */
public interface PipeFactory {
	
  /**
   * 创建连接到Server的连接
   */
  public Pipe connect(String serverHost,int serverPort,PipeListener pipeListener)throws IOException;

  /**
   * 在某个端口上建立一个网络Server
   */
  public PipeServer createServer(int port,PipeServerListener pipeServerListener,PipeListener pipeListener)throws IOException;
  
}
