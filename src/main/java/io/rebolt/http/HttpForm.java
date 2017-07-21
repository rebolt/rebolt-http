package io.rebolt.http;

import com.google.common.collect.Maps;
import io.rebolt.core.models.IModel;
import io.rebolt.core.utils.HashUtil;
import io.rebolt.core.utils.ObjectUtil;
import io.rebolt.core.utils.StringUtil;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

import static io.rebolt.core.constants.Constants.STRING_AND;
import static io.rebolt.core.constants.Constants.STRING_EMPTY;
import static io.rebolt.core.constants.Constants.STRING_EQUAL;
import static io.rebolt.core.utils.StringUtil.decodeUrl;
import static io.rebolt.core.utils.StringUtil.split;

/**
 * Http 통신시 사용하는 Form 데이터를 관리할 수 있다
 *
 * @since 1.0.0
 */
@ToString
public final class HttpForm implements IModel<HttpForm> {
  private static final long serialVersionUID = 3140816378576917540L;
  private @Getter Map<String, String> queryMap;

  public static HttpForm create() {
    return new HttpForm();
  }

  public static HttpForm parse(String queryString) {
    if (StringUtil.isNullOrEmpty(queryString)) {
      return create();
    }
    List<String> queryList = split('&', queryString);
    Map<String, String> queryMap = Maps.newHashMapWithExpectedSize(queryList.size());
    queryList.forEach(entry -> {
      List<String> items = split('=', entry);
      queryMap.put(decodeUrl(items.get(0)), decodeUrl(items.get(1)));
    });
    return create().addAll(queryMap);
  }

  private HttpForm() {
    this.queryMap = Maps.newHashMap();
  }

  /**
   * 쿼리 추가 만약 key가 존재하다면 교체된다. 만약 value가 null이면 key가 저장되지 않는다.
   *
   * @param key 키
   * @param value 값
   * @return {@link HttpForm}
   */
  public HttpForm add(String key, String value) {
    if (value != null) {
      queryMap.put(key, value);
    }
    return this;
  }

  /**
   * 쿼리 추가 만약 key가 존재한다면 교체된다. 만약 value가 null이면 Key가 저장되지 않는다.
   *
   * @param key 키
   * @param value 값
   * @return {@link HttpForm}
   */
  public HttpForm add(String key, Object value) {
    if (value != null) {
      queryMap.put(key, value.toString());
    }
    return this;
  }

  /**
   * 쿼리 추가 만약 key가 존재한다면 추가되지 않는다.
   *
   * @param key 키
   * @param value 값
   * @return {@link HttpForm}
   */
  public HttpForm addIfAbsent(String key, String value) {
    queryMap.putIfAbsent(key, value);
    return this;
  }

  /**
   * 대량 쿼리 추가
   *
   * @param queryMap {@link Map}
   * @return {@link HttpForm}
   */
  public HttpForm addAll(Map<String, String> queryMap) {
    this.queryMap.putAll(queryMap);
    return this;
  }

  /**
   * Form 문자열로 변환
   *
   * @return Form 문자열
   */
  public final String toFormString() {
    if (ObjectUtil.isEmpty(queryMap)) {
      return STRING_EMPTY;
    }
    StringBuilder builder = new StringBuilder();
    queryMap.forEach((key, value) -> builder.append(key).append(STRING_EQUAL).append(StringUtil.encodeUrl(value)).append(STRING_AND));
    return builder.length() > 0 ? builder.substring(0, builder.length() - 1) : STRING_EMPTY;
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
