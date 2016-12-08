package io.rebolt.http.fluent;

import com.google.common.collect.Maps;
import io.rebolt.core.exceptions.IllegalParameterException;
import io.rebolt.core.exceptions.NotImplementedException;
import io.rebolt.http.HttpHeader;
import io.rebolt.http.HttpMethod;
import io.rebolt.http.HttpResponse;
import io.rebolt.http.factories.SyncFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static io.rebolt.http.HttpMethod.Delete;
import static io.rebolt.http.HttpMethod.Get;
import static io.rebolt.http.HttpMethod.Head;
import static io.rebolt.http.HttpMethod.Post;
import static io.rebolt.http.HttpMethod.Put;
import static io.rebolt.http.HttpRequest.create;
import static io.rebolt.http.converters.ConverterTable.getBodyType;

/**
 * 복잡한 초기 설정 없이 곧바로 Http 요청을 할 수 있는 인터페이스를 제공한다.
 * <p>
 * <code>
 * 예) HttpResponse<String> response = ReboltHttp.get(String.class).uri("http://nexon.com").call();
 * <p>
 * 예) HttpResponse<String> response = ReboltHttp.post(String.class).uri("http://api.nexon.com").body(formString).call();
 * <p>
 * 예) HttpResponse<JsonNode> response = ReboltHttp.post(JsonNode.class).uri("http://api.nexon.com").header("Authorization", "...").hedaer("X-User-Protocol",
 * "...").body(formString).call();
 * <p>
 * 예) ReboltHttp.get().uri("http://nexon.com").asyncCall(response -> { ... });
 * </code>
 *
 * @since 1.0
 */
public final class ReboltHttp<T> {

  /**
   * 키: host
   * 값: {@link SyncFactory}
   */
  private static Map<String, SyncFactory> syncMap = Maps.newConcurrentMap();

  private final Class<T> responseType;
  private final HttpMethod httpMethod;
  private HttpHeader httpHeader;
  private URI uri;
  private Object body;

  private ReboltHttp(HttpMethod httpMethod, Class<T> responseType) {
    this.httpMethod = httpMethod;
    this.responseType = responseType;
  }

  // region method

  /**
   * Get 방식으로 요청을 생성한다.
   *
   * @return {@link ReboltHttp}
   * @since 1.0
   */
  public static ReboltHttp<String> get() {
    return new ReboltHttp<>(Get, String.class);
  }

  /**
   * Get 방식으로 요청을 생성한다.
   *
   * @param responseType {@link HttpResponse}에서 사용하게될 타입 선언
   * @param <T> {@link HttpResponse} 제네릭 타입
   * @return {@link ReboltHttp}
   * @since 1.0
   */
  public static <T> ReboltHttp<T> get(Class<T> responseType) {
    return new ReboltHttp<>(Get, responseType);
  }

  /**
   * Post 방식으로 요청을 생성한다.
   *
   * @param responseType {@link HttpResponse}에서 사용하게될 타입 선언
   * @param <T> {@link HttpResponse} 제네릭 타입
   * @return {@link ReboltHttp}
   * @since 1.0
   */
  public static <T> ReboltHttp<T> post(Class<T> responseType) {
    return new ReboltHttp<>(Post, responseType);
  }

  /**
   * Head 방식으로 요청을 생성한다.
   *
   * @return {@link ReboltHttp}
   * @since 1.0
   */
  public static ReboltHttp<String> head() {
    return new ReboltHttp<>(Head, String.class);
  }

  /**
   * Head 방식으로 요청을 생성한다.
   *
   * @param responseType {@link HttpResponse}에서 사용하게될 타입 선언
   * @param <T> {@link HttpResponse} 제네릭 타입
   * @return {@link ReboltHttp}
   * @since 1.0
   */
  public static <T> ReboltHttp<T> head(Class<T> responseType) {
    return new ReboltHttp<>(Head, responseType);
  }

  /**
   * Delete 방식으로 요청을 생성한다.
   *
   * @param responseType {@link HttpResponse}에서 사용하게될 타입 선언
   * @param <T> {@link HttpResponse} 제네릭 타입
   * @return {@link ReboltHttp}
   * @since 1.0
   */
  public static <T> ReboltHttp<T> delete(Class<T> responseType) {
    return new ReboltHttp<>(Delete, responseType);
  }

  /**
   * Put 방식으로 요청을 생성한다.
   *
   * @param responseType {@link HttpResponse}에서 사용하게될 타입 선언
   * @param <T> {@link HttpResponse} 제네릭 타입
   * @return {@link ReboltHttp}
   * @since 1.0
   */
  public static <T> ReboltHttp<T> put(Class<T> responseType) {
    return new ReboltHttp<>(Put, responseType);
  }

  // endregion

  // region builder

  /**
   * 웹주소를 설정한다.
   * 잘못된 형식의 웹주소가 전달되면 {@link IllegalParameterException}이 발생한다.
   *
   * @param uri 스키마와 쿼리스트링을 포함한 전체 웹주소(URI)를 전달한다. 예) https://www.nexon.com?key=value
   * @return {@link ReboltHttp}
   * @since 1.0
   */
  public ReboltHttp<T> uri(String uri) {
    try {
      this.uri = new URI(uri);
    } catch (URISyntaxException e) {
      throw new IllegalParameterException(e);
    }
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

  /**
   * Request Body를 설정한다.
   * Body가 없다면 호출하지 않아도 된다.
   *
   * @param body Request Body
   * @return {@link ReboltHttp}
   */
  public ReboltHttp<T> body(Object body) {
    this.body = body;
    return this;
  }

  // endregion

  /**
   * Http 요청에 대한 결과를 얻는다.
   *
   * @return {@link HttpResponse}
   * @since 1.0
   */
  public HttpResponse<T> call() {
    // step 1 : request, response 타입 추측
    Class<?> requestType;
    switch (httpMethod) {
      case Get:
      case Head:
        requestType = void.class;
        break;
      default:
        requestType = getBodyType(body);
        break;
    }

    // step 2 : SyncFactory 호출
    String host = uri.getHost();
    if (!syncMap.containsKey(host)) {
      syncMap.put(host, new SyncFactory());
    }

    // step 4 : HttpRequest 생성 및 호출
    return syncMap.get(host)
        .invoke(create(requestType, responseType)
            .method(httpMethod)
            .uri(uri.toString())
            .body(body)
            .header(httpHeader));
  }

  public void asyncCall(HttpResponse<T> response) {
    throw new NotImplementedException("Not yet. sorry.");
  }

}
