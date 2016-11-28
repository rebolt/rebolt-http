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

import com.google.common.net.MediaType;
import io.rebolt.core.models.IModel;
import io.rebolt.core.utils.HashUtil;
import io.rebolt.core.utils.ObjectUtil;
import io.rebolt.http.converters.Converter;
import io.rebolt.http.converters.ConverterTable;
import lombok.Getter;
import lombok.ToString;

import static io.rebolt.http.HttpMethod.Get;

/**
 * HttpRequest
 *
 * @since 1.0
 */
@ToString
public final class HttpRequest implements IModel<HttpRequest> {
  private static final long serialVersionUID = -8573892752367366044L;
  private final Converter converter;
  private @Getter HttpHeader header;
  private @Getter HttpMethod method;
  private @Getter String url;
  private @Getter String path;
  private @Getter HttpForm form;
  private @Getter Object body;

  public static HttpRequest create() {
    return new HttpRequest();
  }

  public static HttpRequest create(Class<?> responseType) {
    return new HttpRequest(responseType);
  }

  public static HttpRequest create(Class<?> requestType, Class<?> responseType) {
    return new HttpRequest(requestType, responseType);
  }

  private HttpRequest() {
    this(void.class, String.class);
  }

  private HttpRequest(Class<?> responseType) {
    this(void.class, responseType);
  }

  private HttpRequest(Class<?> requestType, Class<?> responseType) {
    this.header = HttpHeader.create();
    this.method = Get;
    this.converter = ConverterTable.get(requestType, responseType);
  }

  // region builders

  public HttpRequest header(HttpHeader header) {
    this.header = header;
    return this;
  }

  public HttpRequest method(HttpMethod method) {
    this.method = method;
    if (!method.equals(Get) && ObjectUtil.isEmpty(header.getContentType())) {
      header.addContentType(MediaType.FORM_DATA);
    }
    return this;
  }

  public HttpRequest accept(MediaType accept) {
    this.header.addAccept(accept);
    return this;
  }

  public HttpRequest accept(String accept) {
    this.header.addAccept(accept);
    return this;
  }

  public HttpRequest contentType(MediaType contentType) {
    this.header.addContentType(contentType);
    return this;
  }

  public HttpRequest contentType(String contentType) {
    this.header.addContentType(contentType);
    return this;
  }

  public HttpRequest url(String url) {
    this.url = url;
    return this;
  }

  public HttpRequest path(String path) {
    this.path = path;
    return this;
  }

  public HttpRequest form(HttpForm form) {
    if (!Get.equals(method)) {
      this.body = form;
    } else {
      this.form = form;
    }
    return this;
  }

  public HttpRequest body(Object body) {
    if (!Get.equals(method)) {
      this.body = body;
    }
    return this;
  }

  // endregion

  /**
   * 목적지의 Endpoint를 가져온다.
   * 만약 {@link HttpMethod}가 Get방식이고, {@link HttpForm}이 추가되었다면 QueryString으로 사용한다.
   *
   * @since 1.0
   */
  public String getEndpointUri() {
    StringBuilder endpoint = new StringBuilder();
    endpoint.append(url);
    ObjectUtil.thenNonEmpty(path, endpoint::append);
    ObjectUtil.thenNonEmpty(form, form -> endpoint.append("?").append(form.toFormString()));
    return endpoint.toString();
  }

  public Converter getConverter() {
    return converter;
  }

  public boolean isBody() {
    return !ObjectUtil.isNull(body);
  }

  @Override
  public boolean isEmpty() {
    return ObjectUtil.isOrNull(method, body) || ObjectUtil.isEmpty(url);
  }

  @Override
  public long deepHash() {
    return HashUtil.deepHash(header, method, url, body);
  }

  @Override
  public boolean equals(HttpRequest httpRequest) {
    return httpRequest != null && httpRequest.deepHash() == this.deepHash();
  }
}

