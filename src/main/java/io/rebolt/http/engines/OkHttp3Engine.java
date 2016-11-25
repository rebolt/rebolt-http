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

package io.rebolt.http.engines;

import io.rebolt.core.utils.ObjectUtil;
import io.rebolt.http.HttpCallback;
import io.rebolt.http.HttpRequest;
import io.rebolt.http.HttpResponse;
import io.rebolt.http.factories.AbstractFactory;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.concurrent.TimeUnit;

/**
 * OkHttp3 통신엔진
 *
 * @since 1.0
 */
final class OkHttp3Engine extends AbstractEngine<Request, Response, Callback> {

  private OkHttpClient client;
  private final Object _lock = new Object();

  /**
   * {@link AbstractFactory}의 설정값을 이용하여 {@link OkHttpClient}를 생성한다.
   *
   * @since 1.0
   */
  private void makeClient() {
    if (client == null) {
      synchronized (_lock) {
        if (client == null) {
          client = new OkHttpClient.Builder()
              .connectTimeout(getConnectionTimeout(), TimeUnit.MICROSECONDS)
              .readTimeout(getReadTimeout(), TimeUnit.MICROSECONDS)
              .connectionPool(new ConnectionPool(getConnectionPoolMaxIdleCount(), getConnectionPoolKeepAliveDuration(), TimeUnit.MILLISECONDS))
              .build();
        }
      }
    }
  }

  @Override
  public Request makeRequest(HttpRequest request) {
    Request.Builder builder = new Request.Builder();
    // step 1 : header
    ObjectUtil.nullGuard(request.getHeader().getHeaders()).forEach(builder::addHeader);
    // step 2 : url and path and query
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
    makeClient();
    return null;
  }

  @Override
  public void invokeAsync(Request request, Callback callback) {
    makeClient();
  }
}
