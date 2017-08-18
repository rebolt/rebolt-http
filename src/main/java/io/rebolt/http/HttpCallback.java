package io.rebolt.http;

import io.rebolt.http.factories.AsyncFactory;

/**
 * {@link AsyncFactory} 에서 사용하는 HttpCallback 인터페이스
 * <p>
 * 비동기로 호출된 요청에 대한 결과객체가 HttpCallback에 담긴다.
 */
@FunctionalInterface
public interface HttpCallback<R, E> {
  void onReceive(HttpResponse<R, E> response);
}
