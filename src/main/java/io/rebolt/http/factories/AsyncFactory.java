package io.rebolt.http.factories;

import io.rebolt.core.utils.ClassUtil;
import io.rebolt.core.utils.ObjectUtil;
import io.rebolt.http.HttpCallback;
import io.rebolt.http.HttpRequest;
import io.rebolt.http.HttpResponse;
import io.rebolt.http.engines.AbstractEngine;
import io.rebolt.http.engines.OkHttp3Engine;

/**
 * 비동기패턴 클라이언트 팩토리
 *
 * @since 1.0
 */
public final class AsyncFactory extends AbstractFactory {

  public AsyncFactory() {
    super.setEngine(ClassUtil.newInstance(OkHttp3Engine.class));
  }

  public AsyncFactory(Class<? extends AbstractEngine> engineClass) {
    super.setEngine(ClassUtil.newInstance(engineClass));
  }

  /**
   * 스레드풀 사이즈
   */
  public void setThreadCount(int threadCount) {
    engine.setThreadCount(threadCount);
  }

  /**
   * 스레드별 유휴시간
   */
  public void setThreadIdleTime(int threadIdleTime) {
    engine.setThreadIdleTime(threadIdleTime);
  }

  /**
   * 비동기요청
   *
   * @param request 요청객체
   * @param callback 콜백 {@link HttpCallback}
   * @param <R> 페이로드 콜백 클래스 (내부에 응답 클래스 포함 : {@link HttpResponse})
   * @since 1.0
   */
  @SuppressWarnings("unchecked")
  public <R> void invoke(HttpRequest request, HttpCallback<R> callback) {
    ObjectUtil.requireNonNull(engine);
    engine.invokeAsync(engine.makeRequest(request), engine.makeCallback(request, callback));
  }

}
