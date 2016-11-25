/*
 * Copyright 2016 The Rebolt Framework
 *
 * The Rebolt Framework licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.rebolt.http.executors;

import io.rebolt.core.exceptions.IllegalParameterException;

import java.util.concurrent.ThreadFactory;

/**
 * 기본 제공하는 {@link ThreadFactory}
 *
 * @since 1.0
 */
public class SimpleThreadFactory implements ThreadFactory {
  private final boolean daemon;
  private final int priority;

  public SimpleThreadFactory() {
    this(true, Thread.NORM_PRIORITY); // default: 5
  }

  public SimpleThreadFactory(boolean daemon, int priority) {
    if (priority < Thread.MIN_PRIORITY || priority > Thread.MAX_PRIORITY) {
      throw new IllegalParameterException("-priority: " + priority + " (expected: 1 ~ 10)");
    }
    this.daemon = daemon;
    this.priority = priority;
  }

  @Override
  public Thread newThread(Runnable runnable) {
    Thread thread = new Thread(runnable);
    try {
      if (thread.isDaemon()) {
        if (!daemon) {
          thread.setDaemon(false);
        }
      } else {
        if (daemon) {
          thread.setDaemon(true);
        }
      }
      if (thread.getPriority() != priority) {
        thread.setPriority(priority);
      }
    } catch (Exception ignored) {
      // ignored..
    }
    return thread;
  }
}
