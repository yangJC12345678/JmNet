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
package org.jmin.test.net.tcp;

import org.jmin.net.PipeListener;
import org.jmin.net.PipeFactory;
import org.jmin.net.PipeServer;
import org.jmin.net.PipeServerListener;
import org.jmin.net.impl.PipeFactoryManager;
import org.jmin.test.net.ServerEventListener;

/**
 * Tcp Server测试例子
 * 
 * 
 * @author Chris Liao 
 */

public class TcpServer {

  /**
   * 测试方法入口
   */
  public static void main(String args[]) throws Exception {
    /**
     * 创建应用连接管理器
     */
  	PipeFactory pipeFactory = PipeFactoryManager.getPipeFactory("tcp");

    /**
     * 创建一个Server事件的监听者
     */
  	PipeServerListener serverListener = new ServerEventListener();
    
    /**
     * 创建一个Server
     */
  	PipeServer server = pipeFactory.createServer(9988,serverListener,(PipeListener)serverListener);
    
    /**
     *让Server运行起来
     */
    server.startup();
    
    Object lock=new Object();
    synchronized(lock){
     while(Thread.currentThread().isAlive())
     lock.wait();
    }
  }
}