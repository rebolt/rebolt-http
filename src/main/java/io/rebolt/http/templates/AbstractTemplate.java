package io.rebolt.http.templates;

import io.rebolt.http.HttpResponse;

/**
 * Http 통신엔진을 추가할 때 {@link AbstractTemplate}를 상속받아 클래스를 생성한다.
 * <p>
 * 생성예제는 {@link OkHttp3Template}를 참고한다.
 *
 * @param <Q> 통신엔진에서 사용하는 Request 클래스
 * @param <R> 통신엔진에서 사용하는 Response 클래스
 */
public abstract class AbstractTemplate<Q, R> {

  /**
   * 요청 객체를 생성한다.
   */
  public abstract Q makeRequest();

  /**
   * 반환 객체를 생성한다.
   *
   * @return {@link HttpResponse} rebolt-http에서 사용하는 반환 객체
   */
  public abstract HttpResponse makeResponse();

  public abstract R internalInvoke(Q request, int retryCount);
}
