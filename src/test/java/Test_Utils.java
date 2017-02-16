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

import com.google.common.net.MediaType;
import io.rebolt.http.utils.HeaderParser;
import org.junit.Test;

import static io.rebolt.core.constants.Constants.CHARSET_UTF8;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class Test_Utils {

  @Test
  public void test_HeaderParser() {
    String accept = "application/json, text/javascript, */*; q=0.01";
    HeaderParser httpHeaderAccept = new HeaderParser(accept);
    assertTrue(httpHeaderAccept.getMediaTypes().size() == 2);
    assertTrue(MediaType.JSON_UTF_8.is(httpHeaderAccept.getFirst().withCharset(CHARSET_UTF8)));
  }

  @Test
  public void test_HeaderParser2() {
    String accept = "application/json; charset=utf-8, text/javascript, */*; q=0.01";
    String accept2 = "application/json; charset=ISO, text/javascript, */*; q=0.01";
    HeaderParser httpHeaderAccept = new HeaderParser(accept);
    HeaderParser httpHeaderAccept2 = new HeaderParser(accept2);
    assertTrue(httpHeaderAccept.getMediaTypes().size() == 2);
    assertTrue(MediaType.JSON_UTF_8.is(httpHeaderAccept.getFirst()));
    assertFalse(MediaType.JSON_UTF_8.is(httpHeaderAccept2.getFirst()));
  }

  @Test
  public void test_HeaderParser3() {
    String accept = "application/json; charset=utf-8, text/javascript, */*; q=0.01";
    HeaderParser httpHeaderAccept = new HeaderParser(accept);

    assertTrue(httpHeaderAccept.isFirst(MediaType.JSON_UTF_8));
    assertTrue(httpHeaderAccept.contains(MediaType.JSON_UTF_8));
    assertTrue(httpHeaderAccept.contains(MediaType.TEXT_JAVASCRIPT_UTF_8));
  }
}
