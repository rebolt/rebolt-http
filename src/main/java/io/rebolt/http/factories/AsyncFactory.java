package io.rebolt.http.factories;

import io.rebolt.http.HttpCallback;
import io.rebolt.http.HttpRequest;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * AsyncFactory
 * <p>
 * 비동기 패턴을 사용하는 {@link AbstractFactory} 추상 클래스
 */
public final class AsyncFactory extends AbstractFactory {

  /**
   * 스레드풀을 사용한다.
   */
  private ThreadPoolExecutor threadPool;

  public AsyncFactory() {
    this.threadPool = null; // 기본 ThreadPool
  }

  public AsyncFactory(ThreadPoolExecutor threadPool) {
    this.threadPool = threadPool;
  }

  protected ThreadPoolExecutor getThreadPool() {
    return threadPool;
  }

  public <Q, R> void invoke(HttpRequest<Q> request, HttpCallback<R> callback) {

  }

}
