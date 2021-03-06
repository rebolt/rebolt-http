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

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.net.MediaType;
import io.rebolt.http.HttpMethod;
import io.rebolt.http.HttpRequest;
import io.rebolt.http.HttpResponse;
import io.rebolt.http.HttpStatus;
import io.rebolt.http.engines.OkHttp3Engine;
import io.rebolt.http.factories.SyncFactory;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public final class Test_SyncFactory {

  @Test
  public void test_get() {
    SyncFactory factory = new SyncFactory(OkHttp3Engine.class);
    HttpRequest request = HttpRequest.create(String.class).uri("https://m-api.nexon.com");
    HttpResponse<String, String> response = factory.invoke(request);

    assertTrue(response.getStatus().equals(HttpStatus.OK_200));
    assertTrue(response.getBody().length() > 0);
  }

  @Test
  public void test_get2() {
    SyncFactory factory = new SyncFactory();
    HttpRequest request = HttpRequest.create(JsonNode.class).uri("https://m-api.nexon.com/signin.nx");
    HttpResponse<JsonNode, JsonNode> response = factory.invoke(request);

    assertTrue(response.getStatus().equals(HttpStatus.NOT_FOUND_404));
    assertTrue(response.getError().get("errorCode").asInt() == -2);
  }
}
