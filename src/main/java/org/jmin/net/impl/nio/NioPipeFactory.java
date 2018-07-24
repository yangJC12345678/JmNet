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
package org.jmin.net.impl.nio;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

import org.jmin.net.Pipe;
import org.jmin.net.PipeFactory;
import org.jmin.net.PipeListener;
import org.jmin.net.PipeServer;
import org.jmin.net.PipeServerListener;
import org.jmin.net.impl.util.PipeUtil;

/**
 * 管道工厂
 * 
 * @author Chris 
 */
public class NioPipeFactory implements PipeFactory{
	
  /**
   * 连接到NIO Server
   */
  public Pipe connect(String serverHost,int serverPort,PipeListener pipeEventListener) throws IOException {
  	if(PipeUtil.getJavaVersion()<1.4)
   	 throw new UnsupportedClassVersionError("JRE version must be more than 1.4");
  	else {
	    SocketChannel socketChannel = SocketChannel.open();
	    socketChannel.connect(new InetSocketAddress(serverHost, serverPort));
	    while(!socketChannel.finishConnect());
	    if(socketChannel.isConnected()) {
	      return new NioPipe(socketChannel,pipeEventListener);
	    } else {
	      throw new ConnectException("Connection refused: connect");
	    }
  	}
  }
 
  /**
   * 在某个端口上建立一个网络Server
   */
  public PipeServer createServer(int port,PipeServerListener serverEventListener,PipeListener pipeEventListener) throws IOException{
    if(PipeUtil.getJavaVersion()<1.4)
      throw new UnsupportedClassVersionError("JRE version must be than 1.4");
     else {
    	return new NioPipeServer(port,serverEventListener,pipeEventListener);
    }
  }
}
