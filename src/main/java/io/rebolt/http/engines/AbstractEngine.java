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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.rebolt.core.utils.ObjectUtil;
import io.rebolt.core.utils.ReflectionUtil;
import io.rebolt.http.HttpCallback;
import io.rebolt.http.HttpRequest;
import io.rebolt.http.HttpResponse;
import io.rebolt.http.HttpStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;
import java.util.Set;

import static io.rebolt.http.HttpStatus.GATEWAY_TIMEOUT_504;
import static io.rebolt.http.HttpStatus.REQUEST_TIMEOUT_408;
import static io.rebolt.http.HttpStatus.TOO_MANY_REQUESTS_429;

/**
 * Http 통신엔진을 추가할 때 {@link AbstractEngine}를 상속받아 클래스를 생성한다.
 * <p>
 * 생성예제는 {@link OkHttp3Engine}를 참고한다.
 *
 * @param <RQ> 통신엔진에서 사용하는 Request 클래스
 * @param <RS> 통신엔진에서 사용하는 Response 클래스
 * @param <CB> 통신엔진에서 사용하는 Callback 클래스
 * @since 1.0
 */
public abstract class AbstractEngine<RQ, RS, CB> {

  private @Getter Class<RQ> request = ReflectionUtil.typeFinder(getClass(), 0);
  private @Getter Class<RS> response = ReflectionUtil.typeFinder(getClass(), 1);
  private @Getter Class<CB> callback = ReflectionUtil.typeFinder(getClass(), 2);

  /**
   * 요청 실패시, 재시도를 시도하는 수를 정의한다.
   * 모든 요청 실패시 재시도 하는 것이 아닌 retryStatus를 이용해 재시도 여부를 판단한다.
   * <p>
   * 기본값 : 3회
   */
  private @Getter @Setter int retryCount = 3;

  /**
   * 커넥션 타임아웃 설정
   * <p>
   * 기본값 : 3,000 milliseconds
   */
  private @Getter @Setter int connectionTimeout = 3000;

  /**
   * 커넥션 후, 데이터수신 타임아웃 설정
   * <p>
   * 기본값 : 5,000 milliseconds
   */
  private @Getter @Setter int readTimeout = 5000;

  /**
   * 커넥션 후, 데이터송신 타임아웃 설정
   * <p>
   * 기본값 : 5,000 milliseconds
   */
  private @Getter @Setter int writeTimeout = 5000;

  /**
   * 커넥션풀의 최대 대기 수 (통신엔진에 따라 적용여부 달라짐)
   * <p>
   * 기본값 : 10
   */
  private @Getter @Setter int connectionPoolMaxIdleCount = 10;

  /**
   * 커넥션풀의 Keep Alive 주가
   * <p>
   * 기본값 : 2,000 milliseconds
   */
  private @Getter @Setter int connectionPoolKeepAliveDuration = 2000;

  /**
   * 재시도 {@link HttpStatus} 정의
   * <p>
   * 기본값 : 408 (Request timeout), 429 (Too many requests), 504 (Gateway timeout)
   */
  private Set<HttpStatus> retryStatus = Sets.newHashSet(REQUEST_TIMEOUT_408, TOO_MANY_REQUESTS_429, GATEWAY_TIMEOUT_504);

  public boolean containsRetryStatus(HttpStatus status) {
    return retryStatus.contains(status);
  }

  public void addRetryStatus(HttpStatus... statuses) {
    if (!ObjectUtil.isNull(statuses)) {
      retryStatus.addAll(Lists.newArrayList(statuses));
    }
  }

  /**
   * 요청에 할당된 스레드를 1~2초사이의 Sleep을 진행한다
   *
   * @since 1.0
   */
  protected void goSleep() {
    try {
      Thread.sleep(new Random().nextInt(1000) + 1000); // 1~2초 사이
    } catch (InterruptedException ignored) {
      // ignored...
    }
  }

  /**
   * 요청 객체를 생성한다.
   *
   * @since 1.0
   */
  public abstract RQ makeRequest(HttpRequest request);

  /**
   * 반환 객체를 생성한다.
   *
   * @return {@link HttpResponse} rebolt-http에서 사용하는 반환 객체
   * @since 1.0
   */
  public abstract HttpResponse makeResponse(HttpRequest request, RS response);

  /**
   * 콜백 객체를 생성한다.
   *
   * @param callback {@link HttpCallback} rebolt-http 사용하는 콜백 객체
   * @since 1.0
   */
  public abstract CB makeCallback(HttpCallback callback);

  /**
   * 요청
   *
   * @param request 통신엔진에서 사용하는 Request 인스턴스
   * @return 통신엔진에서 사용하는 Response 인스턴스
   * @since 1.0
   */
  public abstract RS invoke(RQ request);

  /**
   * 비동기 요청
   *
   * @param request 통신엔진에서 사용하는 Request 인스턴스
   * @param callback 통신엔진에서 사용하는 Callback 인스턴스
   * @since 1.0
   */
  public abstract void invokeAsync(RQ request, CB callback);
}
