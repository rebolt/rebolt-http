/*
 * Copyright 2016 The Rebolt Framework
 *
 * The Rebolt Framework licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

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
 *
 * @param <R> 응답 클래스
 * @since 1.0
 */
@ToString
public final class HttpResponse<R> implements IModel<HttpResponse> {
  private static final long serialVersionUID = 7879572529075814407L;
  private @Getter @Setter HttpStatus status;
  private @Getter @Setter HttpHeader header;
  private @Getter @Setter R body;
  /**
   * 요청에 대한 예상되지 않은 에러가 발생했을 때 {@link HttpException} 객체가 생성된다.
   * 정상적으로 데이터를 주고 받았을 때에는 null로 정의된다.
   * 예상되지 않은 에러는 주로 네트워크 타임아웃등이 포함된다.
   */
  private @Getter @Setter HttpException exception;

  public HttpResponse() {}

  public HttpResponse(int status, HttpHeader header, R body) {
    this.status = HttpStatus.lookup(status);
    this.header = header;
    this.body = body;
  }

  public HttpResponse(HttpException exception) {
    this.status = exception.getStatus();
    this.exception = exception;
  }

  public boolean hasError() {
    return status.hasError() || !ObjectUtil.isNull(exception) && exception.hasError();
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
