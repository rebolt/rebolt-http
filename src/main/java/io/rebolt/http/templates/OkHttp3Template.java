package io.rebolt.http.templates;

import io.rebolt.http.HttpResponse;

/**
 * OkHttp3 라이브러리를 사용한 {@link AbstractTemplate}
 */
public final class OkHttp3Template extends AbstractTemplate {
  @Override
  public Object makeRequest() {
    return null;
  }

  @Override
  public HttpResponse makeResponse() {
    return null;
  }

  @Override
  public Object internalInvoke(Object request, int retryCount) {
    return null;
  }
}
