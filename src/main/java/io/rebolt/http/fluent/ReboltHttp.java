package io.rebolt.http.fluent;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;
import io.rebolt.core.exceptions.IllegalParameterException;
import io.rebolt.core.exceptions.NotInitializedException;
import io.rebolt.core.utils.ObjectUtil;
import io.rebolt.core.utils.StringUtil;
import io.rebolt.http.HttpForm;
import io.rebolt.http.HttpHeader;
import io.rebolt.http.HttpMethod;
import io.rebolt.http.HttpRequest;
import io.rebolt.http.converters.ConverterTable;
import io.rebolt.http.factories.SyncFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static io.rebolt.core.constants.Constants.STRING_AND;
import static io.rebolt.core.constants.Constants.STRING_QUESTION;
import static io.rebolt.http.HttpMethod.*;

/**
 * 복잡한 초기 설정 없이 곧바로 Restful API 요청을 할 수 있다.
 * <p>
 * 사용예) {@code HttpResponse<String> response = ReboltHttp.get(String.class).uri("http://nexon.com").call();} {@code HttpResponse<String> response =
 * ReboltHttp.post(String.class).uri("http://api.nexon.com").body(formString).call();} {@code HttpResponse<String> response =
 * ReboltHttp.get(String.class).uri("http://nexon.com").call();} {@code HttpResponse<String> response = ReboltHttp.post(String.class).uri("http://api.nexon.com").body(formString).call();}
 * {@code HttpResponse<JsonNode> response = ReboltHttp.post(JsonNode.class).uri("http://api.nexon.com").header("Authorization", "...").hedaer("X-User-Protocol",
 * "...").body(formString).call(); } {@code HttpResponse<JsonNode> response = ReboltHttp.post(JsonNode.class).uri("http://api.nexon.com").header("Authorization",
 * "...").hedaer("X-User-Protocol", "...").body(formString).call();} {@code ReboltHttp.get().uri("http://nexon.com").asyncCall(response -> { ... });}
 *
 * @version 1.1.0
 * @since 1.0.0
 */
public final class ReboltHttp<T> {

  /**
   * 키: host 값: {@link SyncFactory}
   */
  private static Map<String, SyncFactory> syncMap = Maps.newConcurrentMap();

  private final Class<T> responseType;
  private final HttpMethod method;
  private HttpHeader httpHeader;
  private String uri;
  private HttpForm query = HttpForm.create();
  private Object body;
  private boolean throwException = false;

  private ReboltHttp(HttpMethod method, Class<T> responseType) {
    this.method = method;
    this.responseType = responseType;
  }

  // region http method

  public static ReboltHttp<JsonNode> get() {
    return new ReboltHttp<>(Get, JsonNode.class);
  }

  public static <T> ReboltHttp<T> get(Class<T> responseType) {
    return new ReboltHttp<>(Get, responseType);
  }

  public static ReboltHttp<JsonNode> post() {
    return new ReboltHttp<>(Post, JsonNode.class);
  }

  public static <T> ReboltHttp<T> post(Class<T> responseType) {
    return new ReboltHttp<>(Post, responseType);
  }

  public static ReboltHttp<JsonNode> head() {
    return new ReboltHttp<>(Head, JsonNode.class);
  }

  public static <T> ReboltHttp<T> head(Class<T> responseType) {
    return new ReboltHttp<>(Head, responseType);
  }

  public static ReboltHttp<JsonNode> delete() {
    return new ReboltHttp<>(Delete, JsonNode.class);
  }

  public static <T> ReboltHttp<T> delete(Class<T> responseType) {
    return new ReboltHttp<>(Delete, responseType);
  }

  public static ReboltHttp<JsonNode> put() {
    return new ReboltHttp<>(Put, JsonNode.class);
  }

  public static <T> ReboltHttp<T> put(Class<T> responseType) {
    return new ReboltHttp<>(Put, responseType);
  }

  // endregion

  // region builder

  /**
   * @param uri https://www.nexon.com?k=v
   */
  public ReboltHttp<T> uri(String uri) {
    this.uri = uri;
    return this;
  }

  public ReboltHttp<T> query(String key, String value) {
    query.add(key, value);
    return this;
  }

  public ReboltHttp<T> exception(boolean throwException) {
    this.throwException = throwException;
    return this;
  }

  public ReboltHttp<T> header(String name, String value) {
    if (httpHeader == null) {
      httpHeader = HttpHeader.create();
    }
    httpHeader.add(name, value);
    return this;
  }

  public ReboltHttp<T> header(String name, List<String> values) {
    if (httpHeader == null) {
      httpHeader = HttpHeader.create();
    }
    httpHeader.add(name, values);
    return this;
  }

  public ReboltHttp<T> body(Object body) {
    this.body = body;
    return this;
  }

  // endregion

  public RestResponse<T> call() {
    // step 1 : requestType 추론
    Class<?> requestType;
    switch (method) {
      case Get:
      case Head:
        requestType = void.class;
        break;
      default:
        requestType = ConverterTable.getBodyType(body);
        break;
    }

    // step 2 : fullUri 계산
    if (StringUtil.isNullOrEmpty(uri)) {
      throw new NotInitializedException(".uri()");
    }
    URI fullUri;
    try {
      if (ObjectUtil.isEmpty(query)) {
        fullUri = new URI(uri);
      } else {
        if (uri.contains(STRING_QUESTION)) {
          fullUri = new URI(uri + STRING_AND + query.toFormString());
        } else {
          fullUri = new URI(uri + STRING_QUESTION + query.toFormString());
        }
      }
    } catch (URISyntaxException e) {
      throw new IllegalParameterException(e);
    }

    // step 2 : SyncFactory 호출
    String host = fullUri.getHost();
    if (!syncMap.containsKey(host)) {
      syncMap.put(host, new SyncFactory());
    }

    // step 3 : HttpRequest 생성 및 호출
    return new RestResponse<>(
        syncMap.get(host).invoke(
            HttpRequest.create(requestType, responseType)
                .method(method)
                .uri(fullUri.toString())
                .body(body)
                .header(httpHeader)),
        throwException);
  }

}
