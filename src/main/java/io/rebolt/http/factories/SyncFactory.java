package io.rebolt.http.factories;

import io.rebolt.core.utils.ClassUtil;
import io.rebolt.http.HttpRequest;
import io.rebolt.http.HttpResponse;
import io.rebolt.http.engines.AbstractEngine;

import java.util.Objects;

/**
 * 동기패턴 클라이언트 팩토리
 *
 * @since 1.0
 */
public final class SyncFactory extends AbstractFactory {

  public SyncFactory(Class<? extends AbstractEngine> engineClass) {
    super.setEngine(ClassUtil.newInstance(engineClass));
  }

  /**
   * 요청
   *
   * @param httpRequest 요청객체
   * @param <R> 페이로드 응답클래스
   * @return {@link HttpResponse}
   * @since 1.0
   */
  @SuppressWarnings("unchecked")
  public <R> HttpResponse invoke(HttpRequest httpRequest) {
    Objects.requireNonNull(engine);
    return (HttpResponse<R>) engine.makeResponse(engine.invoke(engine.makeRequest(httpRequest)));
  }
}
