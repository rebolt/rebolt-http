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

package io.rebolt.http;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@ToString
public enum HttpStatus {

  // 1xx
  CONTINUE_100(100, "continue"),

  // 2xx
  OK_200(200, "ok"),
  NO_CONTENT_204(204, "no_content"),

  // 3xx
  FOUND_302(302, "found"),

  // 4xx
  BAD_REQUEST_400(400, "bad_request"),
  UNAUTHORIZED_401(401, "unauthorized"),
  FORBIDDEN_403(403, "forbidden"),
  NOT_FOUND_404(404, "not_found"),
  METHOD_NOT_ALLOWED_405(405, "method_not_allowed"),
  NOT_ACCEPTABLE_406(406, "not_acceptable"),
  REQUEST_TIMEOUT_408(408, "request_timeout"),
  TOO_MANY_REQUESTS_429(429, "too_many_requests"),

  REQUEST_FAILED_499(499, "request_failed"),

  // 5xx
  INTERNAL_SERVER_ERROR_500(500, "internal_server_error"),
  NOT_IMPLEMENTED_501(501, "not_implemented"),
  SERVICE_UNAVAILABLE_503(503, "service_unavailable"),
  GATEWAY_TIMEOUT_504(504, "gateway_timeout");

  HttpStatus(int code, String status) {
    this.code = code;
    this.status = status;
  }

  private final @Getter int code;
  private final @Getter String status;

  public boolean hasError() {
    return code < 200 || code >= 400;
  }

  public static HttpStatus lookup(Integer code) {
    return codeMap.get(code);
  }

  private static final Map<Integer, HttpStatus> codeMap = Maps.newHashMapWithExpectedSize(HttpStatus.values().length);

  static {
    for (HttpStatus entry : HttpStatus.values()) {
      codeMap.put(entry.getCode(), entry);
    }
  }

}
