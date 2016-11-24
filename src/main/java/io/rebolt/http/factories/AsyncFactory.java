package io.rebolt.http.factories;

import io.rebolt.core.utils.ClassUtil;
import io.rebolt.http.HttpCallback;
import io.rebolt.http.HttpRequest;
import io.rebolt.http.HttpResponse;
import io.rebolt.http.executors.LinkedBlockingThreadExecutor;
import io.rebolt.http.executors.SimpleThreadFactory;
import io.rebolt.http.engines.AbstractEngine;
import lombok.Getter;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

/**
 * AsyncFactory
 * <p>
 * 비동기 패턴을 사용하는 {@link AbstractFactory} 추상 클래스
 *
 * @since 1.0
 */
public final class AsyncFactory extends AbstractFactory {

  private @Getter ExecutorService threadPool;

  public AsyncFactory(Class<? extends AbstractEngine> templateClass) {
    super.setEngine(ClassUtil.newInstance(templateClass));
  }

  public void setThreadPool(final ExecutorService threadPool) {
    this.threadPool = threadPool;
  }

  public void setThreadPool(int maxThread, int threadIdleMillis, int maxQueue) {
    this.threadPool = new LinkedBlockingThreadExecutor.Builder()
        .setThreadCount(maxThread)
        .setThreadFactory(new SimpleThreadFactory())
        .setThreadIdleTime(threadIdleMillis)
        .setLinkedBlockingQueue(maxQueue).build();
  }

  /**
   * 비동기요청
   *
   * @param request 요청객체
   * @param callback 콜백 {@link HttpCallback}
   * @param <Q> 페이로드 요청 클래스
   * @param <R> 페이로드 콜백 클래스 (내부에 응답 클래스 {@link HttpResponse})
   * @since 1.0
   */
  @SuppressWarnings("unchecked")
  public <Q, R> void invoke(HttpRequest<Q> request, HttpCallback<R> callback) {
    Objects.requireNonNull(engine);
    engine.invokeAsync(engine.makeRequest(request), engine.makeCallback(callback));
  }

}
