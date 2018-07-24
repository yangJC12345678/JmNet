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

/**
 * Server上发生异常
 * 
 * @author Chris
 */
public class ServerErrorEvent extends ServerEvent {

  /**
   * caused exception
   */
  public Throwable detail;
  
  /**
   * 构造函数
   */
  public ServerErrorEvent(int serverPort,Throwable detail) {
    super(serverPort);
    this.detail = detail;
  }

  /**
   * Return caused throwable.
   */
  public Throwable getCause() {
    return detail;
  }

  /**
   * override method
   */
  public String toString() {
    return "Error occured in server port:" + this.getPort() + ",caused: " +detail.toString();
  }
}