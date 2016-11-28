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

package io.rebolt.http.converters;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;
import io.rebolt.core.utils.ClassUtil;
import io.rebolt.http.HttpForm;

import java.util.Map;

/**
 * rebolt-http에서 제공하는 기본 {@link Converter} 목록
 * 초기화 런타임시점(static)에 모든 {@link Converter}를 활성화시킨다.
 *
 * @since 1.0
 */
public final class ConverterTable {
  /**
   * 이중 Map
   * <p>
   * Guava에서 제공하는 Table보다는 이중 Map으로 구성하는 것이 속도면에서 유리하다.
   */
  private static final Map<Class, Map<Class, Converter>> converterTable = Maps.newHashMap();

  static {
    /**
     * {@link StringConverter}
     */
    add(String.class, String.class, StringConverter.class);
    add(void.class, String.class, StringConverter.class);

    /**
     * {@link FormToJsonConverter}
     */
    add(HttpForm.class, JsonNode.class, FormToJsonConverter.class);

    /**
     * {@link JsonConverter}
     */
    add(JsonNode.class, JsonNode.class, JsonConverter.class);

    /**
     * {@link StringToJsonConverter}
     */
    add(String.class, JsonNode.class, StringToJsonConverter.class);
    add(void.class, String.class, StringToJsonConverter.class);
  }

  /**
   * {@link Converter} 추가
   *
   * @param requestType 요청 페이로드
   * @param responseType 응답 페이로드
   * @param converterType 컨버터타입
   * @since 1.0
   */
  public static synchronized void add(Class<?> requestType, Class<?> responseType, Class<? extends Converter> converterType) {
    Map<Class, Converter> converterMap = converterTable.get(requestType);
    if (converterMap == null) {
      converterMap = Maps.newHashMap();
      converterMap.put(responseType, ClassUtil.newInstance(converterType));
      converterTable.put(requestType, converterMap);
    } else {
      // 존재한다면 덮어쓴다.
      converterMap.put(responseType, ClassUtil.newInstance(converterType));
    }
  }

  /**
   * {@link Converter} 조회
   *
   * @param requestType 요청 페이로드
   * @param responseType 응답 페이로드
   * @since 1.0
   */
  public static Converter get(Class<?> requestType, Class<?> responseType) {
    Map<Class, Converter> converterMap = converterTable.get(requestType);
    if (converterMap == null) {
      return null;
    }
    return converterMap.get(responseType);
  }
}
