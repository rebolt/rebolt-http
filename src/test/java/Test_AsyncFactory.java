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
import io.rebolt.http.HttpRequest;
import io.rebolt.http.HttpResponse;
import io.rebolt.http.HttpStatus;
import io.rebolt.http.factories.AsyncFactory;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public final class Test_AsyncFactory {

  @Test
  public void test_get() {
    AsyncFactory factory = new AsyncFactory();
    HttpRequest request = HttpRequest.create().uri("https://m-api.nexon.com");

    Thread mainThread = Thread.currentThread();
    factory.invoke(request, (HttpResponse<String, String> response) -> {
      try {
        if (!response.hasError()) {
          assertTrue(response.getStatus().equals(HttpStatus.OK_200));
        }
      } finally {
        mainThread.interrupt();
      }
    });

    try {
      Thread.sleep(Integer.MAX_VALUE);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void test_get2() {
    AsyncFactory factory = new AsyncFactory();
    HttpRequest request = HttpRequest.create(JsonNode.class).uri("https://m-api.nexon.com/signin.nx");

    Thread mainThread = Thread.currentThread();
    factory.invoke(request, (HttpResponse<JsonNode, JsonNode> response) -> {
      try {
        if (!response.hasError()) {
          assertTrue(response.getStatus().equals(HttpStatus.OK_200));
          assertTrue(response.getBody().get("errorCode").asInt() == -2);
        }
      } finally {
        mainThread.interrupt();
      }
    });

    try {
      Thread.sleep(Integer.MAX_VALUE);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
