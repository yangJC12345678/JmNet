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

import java.io.IOException;

/**
 * 读超时异常
 *
 * @author Chris
 */
public class ReadTimeoutException extends IOException {

  /**
   * 构造函数
   */
  public ReadTimeoutException() {
    super();
  }

  /**
   * 构造函数
   */
  public ReadTimeoutException(String s) {
    super(s);
  }
}