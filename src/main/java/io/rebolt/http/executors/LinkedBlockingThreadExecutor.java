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

import io.rebolt.core.exceptions.NotInitializedException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * LinkedBlockingThreadExecutor
 * <p>
 * 요청을 큐에 쌓는다. 사용가능한 스레드가 있을 때, 큐에 쌓인 요청을 순서대로 처리한다.
 * 스레드풀이 처리 가능한 속도보다 요청이 더 빨리 쌓일 경우, 요청큐가 계속 증가하는 구조다.
 * 요청큐가 증가하더라도 스레드풀 내의 스레드 숫자에는 영향을 미치지 않는다. (힙메모리 증가)
 * <p>
 * 요청큐의 기본 갯수는 1,000,000개로 서버의 메모리상태, 스레드풀정책에 따라 적절히 조정할
 * 수 있다.
 *
 * @since 1.0
 */
public final class LinkedBlockingThreadExecutor extends ThreadPoolExecutor {
  /**
   * 요청큐의 기본 최대크기
   * <p>
   * 만약 사용자로부터 기본값보다 더 낮은 값이 주어졌을 경우, 기본값으로 변경된다.
   */
  private static final int DEFAULT_QUEUE_MAX_SIZE = 1_000_000;

  private LinkedBlockingThreadExecutor(int threadCount, int threadIdleTime, BlockingQueue<Runnable> queue, ThreadFactory threadFactory) {
    super(threadCount, threadCount, threadIdleTime, TimeUnit.MILLISECONDS, queue, threadFactory);
  }

  /**
   * {@link LinkedBlockingThreadExecutor} Builder
   */
  public static final class Builder {
    private int threadCount;
    private int threadIdleTime;
    private BlockingQueue<Runnable> blockingQueue;
    private ThreadFactory threadFactory;

    public Builder setThreadCount(int threadCount) {
      this.threadCount = threadCount;
      return this;
    }

    public Builder setThreadIdleTime(int threadIdleTime) {
      this.threadIdleTime = threadIdleTime;
      return this;
    }

    public Builder setLinkedBlockingQueue(int maxSize) {
      if (maxSize < DEFAULT_QUEUE_MAX_SIZE) {
        maxSize = DEFAULT_QUEUE_MAX_SIZE;
      }
      this.blockingQueue = new LinkedBlockingQueue<>(maxSize);
      return this;
    }

    public Builder setThreadFactory(ThreadFactory threadFactory) {
      this.threadFactory = threadFactory;
      return this;
    }

    public LinkedBlockingThreadExecutor build() {
      if (blockingQueue == null || threadFactory == null) {
        throw new NotInitializedException("blockingQueue or threadFactory");
      }
      return new LinkedBlockingThreadExecutor(threadCount, threadIdleTime, blockingQueue, threadFactory);
    }
  }

}
