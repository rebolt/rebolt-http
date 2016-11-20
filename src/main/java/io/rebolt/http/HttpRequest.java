package io.rebolt.http;

import io.rebolt.core.models.IModel;
import io.rebolt.core.utils.HashUtil;
import io.rebolt.core.utils.StringUtil;
import lombok.Getter;

import java.util.Objects;

/**
 * HttpRequest
 * <p>
 * Http 요청을 하기 위해서는 {@link HttpRequest} 인스턴스를 생성한다.
 */
public final class HttpRequest<T> implements IModel<HttpRequest> {
  private static final long serialVersionUID = -8573892752367366044L;
  private @Getter HttpHeader header;
  private @Getter HttpMethod method;
  private @Getter String uri;
  private @Getter T body;

  public static <T> HttpRequest<T> create() {
    return new HttpRequest<>();
  }

  private HttpRequest() {
    this.header = HttpHeader.create();
    this.method = HttpMethod.Get;
  }

  public HttpRequest<T> header(HttpHeader header) {
    this.header = header;
    return this;
  }

  public HttpRequest<T> method(HttpMethod method) {
    this.method = method;
    return this;
  }

  @Override
  public boolean isEmpty() {
    return Objects.isNull(method)
        || Objects.isNull(body)
        || StringUtil.isNullOrEmpty(uri);
  }

  @Override
  public long deepHash() {
    return HashUtil.deepHash(header, method, uri, body);
  }

  @Override
  public boolean equals(HttpRequest httpRequest) {
    return header.equals(httpRequest.getHeader())
        && method.equals(httpRequest.getMethod())
        && uri.equals(httpRequest.getUri())
        && body.equals(httpRequest.getBody());
  }
}

