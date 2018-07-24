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

import java.util.EventObject;

/**
 * Socket server事件
 *
 * @author Chris Liao
 */

public class ServerEvent extends EventObject {
	
 /**
  * Server port
  */
  private int serverPort;

  /**
   * Event constructor
   */
  public ServerEvent(int serverPort) {
  	super("Localhost");
    this.serverPort = serverPort;
  }
  
  /**
   * Return port
   */
  public int getPort(){
	  return this.serverPort;
  }
  
  /**
   * Hash code
   */
  public int hashCode(){
  	return this.serverPort;
  }
  
  /**
   * equals method
   */
  public boolean equals(Object obj){
  	if(obj instanceof ServerEvent){
  		ServerEvent other = (ServerEvent)obj;
  		return (this.serverPort == other.serverPort);
  	}else{
  		return false;
  	}
  }
  
  /**
   * override method
   */
  public String toString(){
	  return "Localhost:" + serverPort;
  }
}
