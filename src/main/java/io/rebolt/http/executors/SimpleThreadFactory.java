package io.rebolt.http.executors;

import io.rebolt.core.exceptions.IllegalParameterException;

import java.util.concurrent.ThreadFactory;

/**
 * 기본 제공하는 {@link ThreadFactory}
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
