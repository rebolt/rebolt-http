package io.rebolt.http.engines;

import io.rebolt.http.HttpCallback;
import io.rebolt.http.HttpRequest;
import io.rebolt.http.HttpResponse;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp3 템플릿
 *
 * @since 1.0
 */
public final class OkHttp3Engine extends AbstractEngine<Request, Response, Callback> {

  @Override
  public Request makeRequest(HttpRequest request) {
    return null;
  }

  @Override
  public HttpResponse makeResponse(Response response) {
    return null;
  }

  @Override
  public Callback makeCallback(HttpCallback callback) {
    return null;
  }

  @Override
  public Response invoke(Request request) {
    return null;
  }

  @Override
  public void invokeAsync(Request request, Callback callback) {

  }
}
