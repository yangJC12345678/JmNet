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

import org.jmin.net.Pipe;
import org.jmin.net.PipeFactory;
import org.jmin.net.impl.PipeFactoryManager;
import org.jmin.test.net.ClientEventListener;

/**
 * TCP连接测试例子
 *
 * @author Chris Liao
 */

public class TcpClient {
	
  /**
   * 测试方法的入口
   */
  public static void main(String args[]) throws Exception {
  	
  	/**
  	 * 发送到Server的消息
  	 */
  	String message ="Hello,Chris";
  	
    /**
     * 创建应用连接管理器
     */
  	PipeFactory pipeFactory = PipeFactoryManager.getPipeFactory("tcp");

    /**
 		 * 通过应用连接管理器创建一个通向远程Server的连接
 		 * 
 		 * 参数：	服务器地址，服务器端口，本地连接的网络事件监听者
 		 */
  	Pipe pipe = pipeFactory.connect("localhost",9988,new ClientEventListener());
  	
  	/**
  	 * 发送消息
  	 */
  	pipe.write(message.getBytes());
  	
  	/**
  	 * 从远程读取消息
  	 */
  	System.out.println(new String(pipe.read()));
  
	  /**
 	  *创建一个同步对象,并用其阻塞当前线程
 	  */
  	pipe.keepListening();
   	pipe.write(message.getBytes());
   	
  	Object lock=new Object();
    synchronized(lock){
      while(Thread.currentThread().isAlive())
      lock.wait();
    }
  }
}