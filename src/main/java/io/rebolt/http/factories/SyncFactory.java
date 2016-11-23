package io.rebolt.http.factories;

import io.rebolt.http.HttpRequest;
import io.rebolt.http.HttpResponse;
import io.rebolt.http.templates.AbstractTemplate;

/**
 * SyncFactory
 * <p>
 * 동기 패턴을 사용하는 {@link AbstractFactory} 추상 클래스
 */
public final class SyncFactory extends AbstractFactory {

  public SyncFactory(final AbstractTemplate template) {
    setTemplate(template);
  }

  public <Q, R> HttpResponse<R> invoke(HttpRequest<Q> httpRequest) {
    return null;
  }

  /**
   * 요청 재시도시 딜레이를 두고 재시도를 한다.
   *
   * @param millis Milliseconds (0 이하의 값이 들어오면 컨텍스트 스위칭만 발생한다)
   */
  private void goSleep(int millis) {
    if (millis < 0) {
      millis = 0;
    }
    try {
      Thread.sleep(millis);
    } catch (InterruptedException ignored) {
      // ignored...
    }
  }
}