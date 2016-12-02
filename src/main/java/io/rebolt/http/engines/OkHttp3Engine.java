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

import io.rebolt.core.utils.LogUtil;
import io.rebolt.core.utils.ObjectUtil;
import io.rebolt.http.HttpCallback;
import io.rebolt.http.HttpHeader;
import io.rebolt.http.HttpMethod;
import io.rebolt.http.HttpRequest;
import io.rebolt.http.HttpResponse;
import io.rebolt.http.HttpStatus;
import io.rebolt.http.exceptions.HttpException;
import io.rebolt.http.factories.AbstractFactory;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

import static io.rebolt.http.HttpMethod.Get;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * OkHttp3 통신엔진
 *
 * @since 1.0
 */
public final class OkHttp3Engine extends AbstractEngine<Request, Response, Callback> {
  private OkHttpClient client;
  private final Object _lock = new Object();

  /**
   * {@link AbstractFactory}의 설정값을 이용하여 {@link OkHttpClient}를 생성한다.
   *
   * @since 1.0
   */
  private OkHttpClient getClient() {
    if (client == null) {
      synchronized (_lock) {
        if (client == null) {
          client = new OkHttpClient.Builder()
              .connectTimeout(getConnectionTimeout(), MILLISECONDS)
              .readTimeout(getReadTimeout(), MILLISECONDS)
              .writeTimeout(getWriteTimeout(), MILLISECONDS)
              .connectionPool(new ConnectionPool(getConnectionPoolMaxIdleCount(), getConnectionPoolKeepAliveDuration(), MILLISECONDS))
              .build();
        }
      }
    }
    return client;
  }

  /**
   * 통신엔진용 요청객체 생성
   *
   * @param httpRequest {@link HttpRequest}
   * @return {@link Request} Okhttp3 에서 사용하는 요청 객체
   * @since 1.0
   */
  @Override
  public Request makeRequest(HttpRequest httpRequest) {
    Request.Builder builder = new Request.Builder();
    // step 1 : header
    ObjectUtil.nullGuard(httpRequest.getHeader().getHeaderMap()).forEach(builder::addHeader);
    // step 2 : url and path and query
    builder.url(httpRequest.getEndpointUri());
    // step 3 : body - 별도의 Null 체크를 하지 않는 이유는 Null Body도 허용하기 위함
    HttpMethod method = httpRequest.getMethod();
    // step 4 : method
    if (method.equals(Get)) {
      builder.get();
    } else {
      @SuppressWarnings("unchecked")
      byte[] bodyBytes = (byte[]) httpRequest.getConverter().convertRequest(httpRequest.getBody());
      RequestBody body = RequestBody.create(MediaType.parse(httpRequest.getHeader().getContentType()), bodyBytes);
      switch (method) {
        default:
          builder.get();
          break;
        case Head:
          builder.head();
          break;
        case Post:
          builder.post(body);
          break;
        case Put:
          builder.put(body);
          break;
        case Delete:
          if (ObjectUtil.isEmpty(bodyBytes)) {
            builder.delete();
          } else {
            builder.delete(body);
          }
          break;
        case Patch:
          builder.patch(body);
          break;
      }
    }
    return builder.build();
  }

  /**
   * 통신엔진용 응답객체 생성
   *
   * @param httpRequest {@link HttpRequest}
   * @param response {@link Response}
   * @since 1.0
   */
  @Override
  public HttpResponse makeResponse(HttpRequest httpRequest, Response response) {
    // step 1 : header
    HttpHeader header = HttpHeader.createForResponse();
    ObjectUtil.nullGuard(response.headers().toMultimap()).forEach(header::add);
    // step 2 : response
    byte[] responseBytes = null;
    try {
      responseBytes = response.body().bytes();
    } catch (IOException ex) {
      LogUtil.warn(ex);
    }
    //noinspection unchecked
    Object responseObject = httpRequest.getConverter().convertResponse(responseBytes);
    //noinspection unchecked
    return new HttpResponse(response.code(), header, responseObject);
  }

  // region sync-invoke

  @Override
  public Response invoke(Request request) {
    return invokeInternal(request, getRetryCount());
  }

  /**
   * 재시도횟수(Retry count)가 포함된 요청
   * - 동기화 방식에서만 사용된다
   *
   * @param request {@link Request}
   * @param retryCount 재시도 수
   * @return {@link Response}
   * @since 1.0
   */
  private Response invokeInternal(Request request, int retryCount) {
    if (retryCount < 0) {
      LogUtil.getLogger().error("-http request retry failed: {}", request.url().toString());
      throw new HttpException(HttpStatus.REQUEST_FAILED_499, "Retry failed");
    }
    Response response;
    try {
      response = getClient().newCall(request).execute();
    } catch (IOException ex) {
      LogUtil.getLogger().warn("-http exception: {}, retry: {}", request.url().toString(), retryCount);
      return invokeInternal(request, --retryCount);
    }
    if (response == null) {
      LogUtil.getLogger().error("-http request failed: {}, null response", request.url().toString());
      throw new HttpException(HttpStatus.REQUEST_FAILED_499, "Null response");
    }
    if (containsRetryStatus(HttpStatus.lookup(response.code()))) {
      LogUtil.getLogger().warn("-http request failed: {}, retry: {}, status: {}", request.url().toString(), retryCount, response.code());
      goSleep();
      return invokeInternal(request, --retryCount);
    }
    return response;
  }

  // endregion

  // region async-invoke

  @Override
  public void invokeAsync(Request request, Callback callback) {
    getClient();
  }

  @Override
  public Callback makeCallback(HttpCallback callback) {
    return null;
  }

  // endregion
}
