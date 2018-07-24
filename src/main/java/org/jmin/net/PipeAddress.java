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

/**
 * We use this class to describe address for communication host
 *
 * @author Chris Liao
 * @version 1.0
 */

public class PipeAddress {

  /**
   * host IP
   */
  private String hostIP;

  /**
   * host name
   */
  private String hostName;
  
  /**
   * 端口
   */
  private int hostPort;
  
  
  /**
   * Constructor
   */
  public PipeAddress(String hostIP,String hostName,int hostPort) {
    this.hostIP=hostIP;
    this.hostName=hostName;
    this.hostPort=hostPort;
  }

  /**
   * Return host IP
   */
  public String getHostIP() {
    return hostIP;
  }
  
  /**
   * Return host name
   */
  public String getHostName() {
    return hostName;
  }
 
  /**
   * Return port
   */
  public int getHostPort() {
    return hostPort;
  }
  
  /**
   * override method
   */
  public String toString() {
    return hostIP+"("+hostName+"):"+hostPort;
  }

  /**
   * override method
   */
  public int hashCode() {
    return hostIP.hashCode()^hostPort;
  }

  /**
   * override method
   */
  public boolean equals(Object obj) {
   if (obj instanceof PipeAddress) {
    	PipeAddress other = (PipeAddress) obj;
      return this.hostIP.equals(other.hostIP)&& this.hostPort == other.hostPort;
   } else {
      return false;
   }
  }
}