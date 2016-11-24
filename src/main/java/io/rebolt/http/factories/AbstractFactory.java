package io.rebolt.http.factories;

import io.rebolt.core.utils.ObjectUtil;
import io.rebolt.http.HttpStatus;
import io.rebolt.http.engines.AbstractEngine;

/**
 * 요청 팩토리
 *
 * @since 1.0
 */
public abstract class AbstractFactory {

  protected AbstractEngine template;

  /**
   * 통신엔진 설정
   *
   * @param template {@link AbstractEngine}
   */
  protected void setTemplate(AbstractEngine template) {
    this.template = template;
  }

  /**
   * 커넥션 타임아웃 설정
   */
  public void setConnectionTimeout(int connectionTimeout) {
    ObjectUtil.requireNonNull(template);
    template.setConnectionTimeout(connectionTimeout);
  }

  /**
   * 타임아웃 설정
   */
  public void setReadTimeout(int readTimeout) {
    ObjectUtil.requireNonNull(template);
    template.setReadTimeout(readTimeout);
  }

  /**
   * 재시도 조건 추가
   *
   * @param httpStatuses {@link HttpStatus}
   */
  public void addRetryStatus(HttpStatus... httpStatuses) {
    ObjectUtil.requireNonNull((Object[]) httpStatuses);
    template.addRetryStatus(httpStatuses);
  }
}
