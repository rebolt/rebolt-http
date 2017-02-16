/*
 * Copyright 2017 The Rebolt Framework
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

package io.rebolt.http.utils;

import com.google.common.collect.Lists;
import com.google.common.net.MediaType;
import io.rebolt.core.utils.StringUtil;
import lombok.Getter;

import java.util.List;

import static io.rebolt.core.utils.ObjectUtil.requireNonNull;

/**
 * HttpHeader 해석기
 *
 * @since 1.0.6
 */
public final class HeaderParser {
  private final @Getter String origin;
  private final @Getter List<MediaType> mediaTypes;

  public HeaderParser(final String headerString) {
    requireNonNull(headerString);
    this.origin = headerString;
    this.mediaTypes = Lists.newLinkedList();
    StringUtil.split(',', headerString).forEach(entry -> {
      try {
        mediaTypes.add(MediaType.parse(entry));
      } catch (Exception ignored) {
      }
    });
  }

  public MediaType getFirst() {
    return mediaTypes.get(0);
  }
}
