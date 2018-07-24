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

import org.jmin.net.Pipe;

/**
 * When exception occurs during net operation
 *
 * @author Chris
 */

public class PipeErrorEvent extends PipeEvent {

	/**
	 * caused exception
	 */
	public Throwable detail;

	/**
	 * Constructor with a source object.
	 */
	public PipeErrorEvent(Pipe source, Throwable detail) {
		super(source);
		this.detail = detail;
	}

	/**
	 *  Return caused throwable.
	 */
	public Throwable getCause() {
		return detail;
	}

	/**
	 * override method
	 * @return
	 */
	public String toString() {
		return detail.toString();
	}
}