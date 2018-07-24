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
package org.jmin.net.impl.udp;

import java.io.IOException;
import java.net.InetAddress;

import org.jmin.net.Pipe;
import org.jmin.net.PipeListener;
import org.jmin.net.PipeFactory;
import org.jmin.net.PipeServer;
import org.jmin.net.PipeServerListener;

/**
 * UDP网络连接工厂
 *
 * @author Chris Liao
 */

public class UdpPipeFactory implements PipeFactory{

	/**
	 * 连接到远程pipe
	 */
	public Pipe connect(String serverHost,int serverPort,PipeListener pipeEventListener)throws IOException{
   return new UdpPipe(InetAddress.getByName(serverHost),serverPort,pipeEventListener);
	}
	
	/**
	* 在某个端口上建立一个网络UDP Server
	*/
	public PipeServer createServer(int port,PipeServerListener serverEventListener,PipeListener pipeEventListener)throws IOException{
		return new UdpPipeServer(port,serverEventListener,pipeEventListener);
	}
}