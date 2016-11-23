package io.rebolt.http.factories;

import io.rebolt.core.utils.ObjectUtil;
import io.rebolt.http.templates.AbstractTemplate;

/**
 * 요청 팩토리
 *
 * @since 1.0
 */
public abstract class AbstractFactory {

  protected AbstractTemplate template;

  /**
   * 통신엔진 설정
   *
   * @param template {@link AbstractTemplate}
   */
  protected void setTemplate(AbstractTemplate template) {
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
}
