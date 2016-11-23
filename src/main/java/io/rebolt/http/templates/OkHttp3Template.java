package io.rebolt.http.templates;

import io.rebolt.http.HttpRequest;
import io.rebolt.http.HttpResponse;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp3 라이브러리를 사용한 {@link AbstractTemplate}
 *
 * @since 1.0
 */
public final class OkHttp3Template extends AbstractTemplate<Request, Response> {
  @Override
  public Request makeRequest(HttpRequest request) {
    return null;
  }

  @Override
  public HttpResponse makeResponse(Response response) {
    return null;
  }

  @Override
  public Response invokeInternal(Request request) {
    return null;
  }
}
