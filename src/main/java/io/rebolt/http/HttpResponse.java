package io.rebolt.http;

import io.rebolt.core.models.IModel;
import io.rebolt.core.utils.HashUtil;
import io.rebolt.core.utils.ObjectUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * HttpResponse
 * <p>
 * {@link HttpRequest} 전달시 반환받는 응답 객체
 */
@ToString
public final class HttpResponse<T> implements IModel<HttpResponse> {
  private static final long serialVersionUID = 7879572529075814407L;
  private @Getter @Setter HttpStatus status;
  private @Getter @Setter HttpHeader header;
  private @Getter @Setter T body;

  public HttpResponse() {}

  public HttpResponse(HttpStatus status, HttpHeader header, T body) {
    this.status = status;
    this.header = header;
    this.body = body;
  }

  public boolean hasError() {
    return ObjectUtil.isNull(status) || status.hasError();
  }

  @Override
  public boolean isEmpty() {
    return ObjectUtil.isNull(status);
  }

  @Override
  public long deepHash() {
    return HashUtil.deepHash(status, header, body);
  }

  @Override
  public boolean equals(HttpResponse httpResponse) {
    return httpResponse != null && httpResponse.deepHash() == this.deepHash();
  }
}
