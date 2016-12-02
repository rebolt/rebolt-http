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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.MediaType;
import io.rebolt.core.utils.LogUtil;
import io.rebolt.core.utils.ObjectUtil;
import io.rebolt.http.HttpForm;

import java.io.IOException;

import static io.rebolt.core.constants.Constants.CHARSET_UTF8;

/**
 * {@link HttpForm} to {@link JsonNode} 컨버터
 *
 * ContentType: "application/x-www-form-urlencoded;charset=utf-8"
 * Accept: "application/json;charset=utf-8"
 */
final class FormToJsonConverter implements BytesConverter<HttpForm, JsonNode> {
  @Override
  public byte[] convertRequest(HttpForm httpForm) {
    if (ObjectUtil.isNull(httpForm)) {
      return new byte[0];
    }
    return httpForm.toFormString().getBytes(CHARSET_UTF8);
  }

  @Override
  public JsonNode convertResponse(byte[] rawResponse) {
    if (ObjectUtil.isNull(rawResponse)) {
      return null;
    }
    try {
      return new ObjectMapper().readTree(rawResponse);
    } catch (IOException e) {
      LogUtil.warn(e);
      return null;
    }
  }

  @Override
  public String getContentType() {
    return MediaType.FORM_DATA.toString();
  }

  @Override
  public String getAccept() {
    return MediaType.JSON_UTF_8.toString();
  }
}
