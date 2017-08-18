package io.rebolt.http.factories;

import io.rebolt.core.utils.ClassUtil;
import io.rebolt.core.utils.ObjectUtil;
import io.rebolt.http.HttpRequest;
import io.rebolt.http.HttpResponse;
import io.rebolt.http.engines.AbstractEngine;
import io.rebolt.http.engines.OkHttp3Engine;

/**
 * 클라이언트 팩토리 (Sync)
 *
 * @since 1.0.0
 */
public final class SyncFactory extends AbstractFactory {

  public SyncFactory() {
    super.setEngine(ClassUtil.newInstance(OkHttp3Engine.class));
  }

  public SyncFactory(Class<? extends AbstractEngine> engineClass) {
    super.setEngine(ClassUtil.newInstance(engineClass));
  }

  @SuppressWarnings("unchecked")
  public <R, E> HttpResponse<R, E> invoke(HttpRequest httpRequest) {
    ObjectUtil.requireNonNull(engine);
    return (HttpResponse<R, E>) engine.makeResponse(httpRequest, engine.invoke(engine.makeRequest(httpRequest)));
  }
}
