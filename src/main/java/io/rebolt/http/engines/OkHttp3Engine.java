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
