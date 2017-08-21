package io.rebolt.http.fluent;

import com.fasterxml.jackson.databind.JsonNode;
import io.rebolt.core.utils.JsonUtil;
import io.rebolt.http.HttpResponse;
import io.rebolt.http.HttpStatus;
import io.rebolt.http.exceptions.HttpException;

/**
 * @since 1.1.0
 */
public final class RestResponse<R> {
  private final HttpResponse<R, JsonNode> response;

  public RestResponse(HttpResponse<R, JsonNode> response, boolean throwException) {
    this.response = response;
    if (throwException && response.hasException()) {
      throw response.getException();
    }
  }

  public boolean hasError() {
    return response.hasError();
  }

  public boolean hasException() {
    return response.hasException();
  }

  public HttpStatus getStatus() {
    return response.getStatus();
  }

  public R getBody() {
    return response.getBody();
  }

  public JsonNode getError() {
    return response.getError();
  }

  public HttpException getException() {
    return response.getException();
  }

  public <E> E getError(Class<E> errorType) {
    return JsonUtil.read(response.getError(), errorType);
  }
}
