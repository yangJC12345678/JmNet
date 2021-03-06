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

import org.jmin.net.event.ServerClosedEvent;
import org.jmin.net.event.ServerCreatedEvent;
import org.jmin.net.event.ServerErrorEvent;

/**
 * server监听接口
 *  
 * @author Chris Liao
 */
public interface PipeServerListener{

  /**
   * When server is created,then occur event
   */
  public void onCreated(ServerCreatedEvent event);

  /**
   * When server is closed,then post the event
   */
  public void onClosed(ServerClosedEvent event);

  /**
   * When server is closed,then post the event
   */
  public void onError(ServerErrorEvent event);

}