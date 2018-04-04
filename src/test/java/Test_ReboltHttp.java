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

import io.rebolt.http.HttpForm;
import io.rebolt.http.HttpStatus;
import io.rebolt.http.fluent.ReboltHttp;
import io.rebolt.http.fluent.RestResponse;
import lombok.Data;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public final class Test_ReboltHttp {

  @Test
  public void test_get() {
    RestResponse<String> response = ReboltHttp.get(String.class).uri("https://m-api.nexon.com").call();

    assertTrue(response.getStatus() == HttpStatus.OK_200);
    assertTrue(response.getBody().length() > 0);
  }

  @Data
  public static class Session {
    private String environment;
    private String server;
    private String version;
  }

  @Data
  public static class ToyResponse {
    private Integer errorCode;
    private String errorText;
    private String errorDetail;
    private Object result;
  }

  @Data
  public class ErrorClass {
    private String error;
  }

  @Test
  public void test_get2() {
    RestResponse<Session> response = ReboltHttp.get(Session.class).uri("https://session.nexon.com").call();
    assertTrue(response.getStatus() == HttpStatus.OK_200);
    assertTrue(response.getBody().getServer().equals("nest-api"));
  }

  @Test
  public void test_get3() {
    RestResponse<ToyResponse> response = ReboltHttp.get(ToyResponse.class).uri("https://m-api.nexon.com/error").query("id", "yours").call();
    assertTrue(response.getStatus() == HttpStatus.NOT_FOUND_404);
    assertTrue(response.getError(ToyResponse.class).errorCode == -2);
  }

  @Test
  public void test_post() {
    RestResponse<ToyResponse> response = ReboltHttp.post(ToyResponse.class).uri("https://m-api.nexon.com/error").body(HttpForm.create().add("id", "yours")).call();
    assertTrue(response.getStatus() == HttpStatus.NOT_FOUND_404);
  }
}