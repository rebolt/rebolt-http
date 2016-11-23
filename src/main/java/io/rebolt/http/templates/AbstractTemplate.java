package io.rebolt.http.templates;

import io.rebolt.http.HttpRequest;
import io.rebolt.http.HttpResponse;

/**
 * Http 통신엔진을 추가할 때 {@link AbstractTemplate}를 상속받아 클래스를 생성한다.
 * <p>
 * 생성예제는 {@link OkHttp3Template}를 참고한다.
 *
 * @param <Q> 통신엔진에서 사용하는 Request 클래스
 * @param <R> 통신엔진에서 사용하는 Response 클래스
 * @since 0.1.0
 */
public abstract class AbstractTemplate<Q, R> {

  private Class<Q> request;
  private Class<R> response;

  /**
   * 요청 객체를 생성한다.
   */
  public abstract Q makeRequest(HttpRequest request);

  /**
   * 반환 객체를 생성한다.
   *
   * @return {@link HttpResponse} rebolt-http에서 사용하는 반환 객체
   */
  public abstract HttpResponse makeResponse(R response);

  /**
   * 통신엔진에 맞는 요청/응답을 처리한다.
   * 기본적으로 retryCount가 있어 그 수만큼 재시도를 처리한다.
   *
   * @param request 통신엔진에서 사용하는 Request 인스턴스
   * @param retryCount 재시도수
   * @return 통신엔진에서 사용하는 Response 인스턴스
   * @since 0.1.0
   */
  public abstract R invokeInternal(Q request, int retryCount);
}
