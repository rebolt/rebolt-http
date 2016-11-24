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

import io.rebolt.core.utils.ObjectUtil;

import static io.rebolt.core.constants.Constants.CHARSET_UTF8;

/**
 * {@link String} to {@link String} 컨버터
 */
public final class StringConverter implements BytesConverter<String, String> {
  @Override
  public byte[] convertRequest(String request) {
    return ObjectUtil.isNull(request) ? new byte[0] : request.getBytes(CHARSET_UTF8);
  }

  @Override
  public String convertResponse(byte[] rawResponse) {
    return ObjectUtil.isNull(rawResponse) ? null : new String(rawResponse, CHARSET_UTF8);
  }
}
