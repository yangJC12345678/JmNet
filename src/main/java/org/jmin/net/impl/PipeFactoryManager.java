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
package org.jmin.net.impl;

import org.jmin.net.PipeFactory;
import org.jmin.net.impl.nio.NioPipeFactory;
import org.jmin.net.impl.tcp.TcpPipeFactory;
import org.jmin.net.impl.udp.UdpPipeFactory;

/**
 * 管道工厂管理中心
 * 
 * @author Chris 
 */
public class PipeFactoryManager {
  
	/**
	 * tcp管道工厂
	 */
	private static PipeFactory tcpPipeFactory = new TcpPipeFactory();
	
	/**
	 * nio格式管道工厂
	 */
	private static PipeFactory nioPipeFactory = new NioPipeFactory();
	
	/**
	 * udp格式管道工厂
	 */
	private static PipeFactory udpPipeFactory = new UdpPipeFactory();
	
	/**
	 * 通过协议查找管道工厂
	 */
	public static PipeFactory getPipeFactory(String mode){
		if("tcp".equalsIgnoreCase(mode)){
			return tcpPipeFactory;
		}else if("nio".equalsIgnoreCase(mode)){
			return nioPipeFactory;
		}else if("udp".equalsIgnoreCase(mode)){
			return udpPipeFactory;
		}else{
			throw new java.lang.RuntimeException("Not support protocol:"+mode);
		}
	}
}
