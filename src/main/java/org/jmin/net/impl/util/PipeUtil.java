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
package org.jmin.net.impl.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

import org.jmin.net.Pipe;

/**
 * Util class
 * 
 * @author Chris
 */

public class PipeUtil {
	
  /**
   * A null string ?
   */
  public static boolean isNull(String value) {
    return (value == null || value.trim().length() ==0)?true:false;
  }
  
  /**
   * get hash Code by array
   */
  public static double getJavaVersion() {
  	return Double.parseDouble(System.getProperty("java.version").substring(0,3));
  }
  
	/**
	 * close Connection
	 */
	public static void close(Pipe pipe) {
		try {
			if(pipe!=null)
			 pipe.close();
		} catch (Throwable e) {
		}
	}
	
	/**
	 * close InputStreamt
	 */
	public static void close(InputStream inputStream) {
		try {
			if(inputStream!=null)
			inputStream.close();
		} catch (Throwable e) {
		}
	}
	
	/**
	 * close outputStream
	 */
	public static void close(OutputStream outputStream) {
		try {
			if(outputStream!=null)
			outputStream.close();
		} catch (Throwable e) {
		}
	}
	
	/**
	 * close writer
	 */
	public static void close(Writer writer) {
		try {
			if(writer!=null)
			writer.close();
		} catch (Throwable e) {
		}
	}
	
	/**
	 * close Reader
	 */
	public static void close(Reader reader) {
		try {
			if(reader!=null)
			 reader.close();
		} catch (Throwable e) {
		}
	}

	/**
	 * close Socket
	 */
	public static void close(Socket socket) {
		try {
			if(socket!=null)
			socket.close();
		} catch (Throwable e) {
		}
	}
	
	/**
	 * close ServerSocket
	 */
	public static void close(ServerSocket socket) {
		try {
			if(socket!=null)
			socket.close();
		} catch (Throwable e) {
		}
	}
	
	/**
	 * close DatagramSocket
	 */
	public static void close(DatagramSocket socket) {
		try {
			if(socket!=null)
			socket.close();
		} catch (Throwable e) {
		}
	}
}
