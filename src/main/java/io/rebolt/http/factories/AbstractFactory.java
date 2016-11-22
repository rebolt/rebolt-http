package io.rebolt.http.factories;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.rebolt.http.HttpStatus;
import io.rebolt.http.templates.AbstractTemplate;

import java.util.Set;

public abstract class AbstractFactory {

  /**
   * 요청 실패시, 재시도를 시도하는 수를 정의한다.
   *
   * 기본값 : 3회
   */
  private int retryCount = 3;

  /**
   * 재시도를 시도할 HttpStatus를 정의한다.
   *
   * 기본값 : 429 (Too many requests), 504 (Gateway timeout)
   */
  private Set<HttpStatus> retryStatus = Sets.newHashSet(HttpStatus.TOO_MANY_REQUESTS_429, HttpStatus.GATEWAY_TIMEOUT_504);

  /**
   * 커넥션을 연결할 때 대기하는 시간을 정의한다.
   *
   * 기본값 : 3000 milliseconds (3초)
   */
  private int connectionTimeout = 3000;

  /**
   * 커넥션 연결 후, 데이터를 읽어올 때의 최장 시간을 정의한다.
   *
   * 기본값 : 5000 milliseconds (5초)
   */
  private int readTimeout = 5000;

  private AbstractTemplate template;

  // region getter, setter

  protected int getRetryCount() {
    return retryCount;
  }

  protected void setRetryCount(int retryCount) {
    this.retryCount = retryCount;
  }

  protected boolean containsRetryStatus(HttpStatus status) {
    return retryStatus.contains(status);
  }

  protected void addRetryStatus(HttpStatus... statuses) {
    retryStatus.addAll(Lists.newArrayList(statuses));
  }

  protected void setRetryStatus(HttpStatus... statuses) {
    retryStatus.clear();
    retryStatus.addAll(Lists.newArrayList(statuses));
  }

  protected int getConnectionTimeout() {
    return connectionTimeout;
  }

  protected void setConnectionTimeout(int connectionTimeout) {
    this.connectionTimeout = connectionTimeout;
  }

  protected int getReadTimeout() {
    return readTimeout;
  }

  protected void setReadTimeout(int readTimeout) {
    this.readTimeout = readTimeout;
  }

  protected AbstractTemplate getTemplate() {
    return template;
  }

  protected void setTemplate(AbstractTemplate template) {
    this.template = template;
  }

  // endregion

}
