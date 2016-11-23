package io.rebolt.http.templates;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.rebolt.core.utils.ReflectionUtil;
import io.rebolt.http.HttpRequest;
import io.rebolt.http.HttpResponse;
import io.rebolt.http.HttpStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Http 통신엔진을 추가할 때 {@link AbstractTemplate}를 상속받아 클래스를 생성한다.
 * <p>
 * 생성예제는 {@link OkHttp3Template}를 참고한다.
 *
 * @param <RQ> 통신엔진에서 사용하는 Request 클래스
 * @param <RS> 통신엔진에서 사용하는 Response 클래스
 * @since 1.0
 */
public abstract class AbstractTemplate<RQ, RS> {

  private @Getter Class<RQ> request = ReflectionUtil.typeFinder(getClass(), 0);
  private @Getter Class<RS> response = ReflectionUtil.typeFinder(getClass(), 1);

  /**
   * 요청 실패시, 재시도를 시도하는 수를 정의한다.
   * <p>
   * 기본값 : 3회
   */
  private @Getter @Setter int retryCount = 3;

  /**
   * 커넥션을 연결할 때 대기하는 시간을 정의한다.
   * <p>
   * 기본값 : 3000 milliseconds (3초)
   */
  private @Getter @Setter int connectionTimeout = 3000;

  /**
   * 커넥션 연결 후, 데이터를 읽어올 때의 최장 시간을 정의한다.
   * <p>
   * 기본값 : 5000 milliseconds (5초)
   */
  private @Getter @Setter int readTimeout = 5000;

  /**
   * 재시도를 시도할 HttpStatus를 정의한다.
   * <p>
   * 기본값 : 429 (Too many requests), 504 (Gateway timeout)
   */
  private Set<HttpStatus> retryStatus = Sets.newHashSet(HttpStatus.TOO_MANY_REQUESTS_429, HttpStatus.GATEWAY_TIMEOUT_504);

  protected boolean containsRetryStatus(HttpStatus status) {
    return retryStatus.contains(status);
  }

  protected void addRetryStatus(HttpStatus... statuses) {
    retryStatus.addAll(Lists.newArrayList(statuses));
  }

  // region abstract methods

  /**
   * 요청 객체를 생성한다.
   */
  public abstract RQ makeRequest(HttpRequest request);

  /**
   * 반환 객체를 생성한다.
   *
   * @return {@link HttpResponse} rebolt-http에서 사용하는 반환 객체
   */
  public abstract HttpResponse makeResponse(RS response);

  /**
   * 통신엔진에 맞는 요청/응답을 처리한다.
   * 기본적으로 retryCount가 있어 그 수만큼 재시도를 처리한다.
   *
   * @param request 통신엔진에서 사용하는 Request 인스턴스
   * @return 통신엔진에서 사용하는 Response 인스턴스
   * @since 0.1.0
   */
  public abstract RS invokeInternal(RQ request);

  // endregion
}
