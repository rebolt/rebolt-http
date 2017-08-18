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
 * @since 1.0.0
 */
@SuppressWarnings("unchecked")
@ToString
@Getter
@Setter
public class HttpResponse<R, E> implements IModel<HttpResponse> {
  private static final long serialVersionUID = 7879572529075814407L;
  private HttpStatus status;
  private HttpHeader header;
  private R body;
  private E error; // handled error message
  private HttpException exception; // unhandled exception

  public HttpResponse() {}

  public HttpResponse(int status, HttpHeader header, R body) {
    this.status = HttpStatus.lookup(status);
    this.header = header;
    if (this.status.hasError()) {
      this.error = (E) body;
    } else {
      this.body = body;
    }
    this.exception = null;
  }

  public HttpResponse(int status, HttpHeader header, R body, E error) {
    this(HttpStatus.lookup(status), header, body, error, null);
  }

  public HttpResponse(HttpException exception) {
    this.status = exception.getStatus();
    this.exception = exception;
  }

  public HttpResponse(HttpStatus status, HttpHeader header, R body, E error, HttpException exception) {
    this.status = status;
    this.header = header;
    this.body = body;
    this.error = error;
    this.exception = exception;
  }

  public boolean hasError() {
    return status.hasError() || !ObjectUtil.isNull(error) || hasException();
  }

  public boolean hasException() {
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
