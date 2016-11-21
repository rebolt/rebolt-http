package io.rebolt.http;

import io.rebolt.core.models.IModel;
import io.rebolt.core.utils.HashUtil;
import io.rebolt.core.utils.ObjectUtil;
import io.rebolt.http.exceptions.HttpException;
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
  /**
   * 요청에 대한 예상되지 않은 에러가 발생했을 때 {@link HttpException} 객체가 생성된다.
   * 정상적으로 데이터를 주고 받았을 때에는 null로 정의된다.
   * 예상되지 않은 에러는 주로 네트워크 타임아웃등이 포함된다.
   */
  private @Getter @Setter HttpException exception;

  public HttpResponse() {}

  public HttpResponse(HttpStatus status, HttpHeader header, T body) {
    this.status = status;
    this.header = header;
    this.body = body;
  }

  public boolean hasError() {
    return !ObjectUtil.isNull(exception) && exception.hasError();
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
