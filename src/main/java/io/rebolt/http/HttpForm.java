package io.rebolt.http;

import com.google.common.collect.Maps;
import io.rebolt.core.models.IModel;
import io.rebolt.core.utils.HashUtil;
import io.rebolt.core.utils.ObjectUtil;
import lombok.Getter;

import java.util.Map;

/**
 * Http 통신시 사용하는 Form 데이터를 관리할 수 있다
 */
public final class HttpForm implements IModel<HttpForm> {
  private static final long serialVersionUID = 3140816378576917540L;
  private @Getter Map<String, String> queryMap;

  public static HttpForm create() {
    return new HttpForm();
  }

  private HttpForm() {
    this.queryMap = Maps.newHashMap();
  }

  /**
   * 쿼리 추가
   * 만약 key가 존재하다면 교체된다.
   *
   * @param key 키
   * @param value 값
   */
  public HttpForm add(String key, String value) {
    queryMap.put(key, value);
    return this;
  }

  /**
   * 쿼리 추가
   * 만약 key가 존재한다면 추가되지 않는다.
   *
   * @param key 키
   * @param value 값
   */
  public HttpForm addIfAbsent(String key, String value) {
    queryMap.putIfAbsent(key, value);
    return this;
  }

  /**
   * 대량 쿼리 추가
   *
   * @param queryMap {@link Map}
   */
  public HttpForm addAll(Map<String, String> queryMap) {
    this.queryMap.putAll(queryMap);
    return this;
  }

  @Override
  public boolean isEmpty() {
    return ObjectUtil.isEmpty(queryMap);
  }

  @Override
  public long deepHash() {
    return HashUtil.deepHash(queryMap);
  }

  @Override
  public boolean equals(HttpForm httpForm) {
    return deepHash() == httpForm.deepHash();
  }
}
