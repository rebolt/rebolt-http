package io.rebolt.http.factories;

import io.rebolt.core.utils.ClassUtil;
import io.rebolt.http.HttpRequest;
import io.rebolt.http.HttpResponse;
import io.rebolt.http.templates.AbstractTemplate;

/**
 * SyncFactory
 * <p>
 * 동기 패턴을 사용하는 {@link AbstractFactory} 추상
 *
 * @since 1.0
 */
public final class SyncFactory extends AbstractFactory {

  public SyncFactory(Class<? extends AbstractTemplate> templateClass) {
    super.setTemplate(ClassUtil.newInstance(templateClass));
  }

  /**
   * 요청
   *
   * @param httpRequest 요청객체
   * @param <Q> 페이로드 요청클래스
   * @param <R> 페이로드 응답클래스
   * @return {@link HttpResponse}
   */
  @SuppressWarnings("unchecked")
  public <Q, R> HttpResponse<R> invoke(HttpRequest<Q> httpRequest) {
    return template.makeResponse(template.invokeInternal(template.makeRequest(httpRequest)));
  }
}
