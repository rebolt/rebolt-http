/*
 *
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
import io.rebolt.http.HttpForm;

/**
 * 엔진에서 사용하는 페이로드를 byte[]로 사용하는 컨버터
 *
 * @param <Request> 페이로드된 요청 프로토콜 ({@link HttpForm}, {@link JsonNode}, {@link String}, ...)
 * @param <Response> 페이로드된 응답 프로토콜 (html, {@link JsonNode}, {@link String}, ...)
 * @since 1.0.0
 */
public interface BytesConverter<Request, Response> extends Converter<Request, byte[], Response> {
}
