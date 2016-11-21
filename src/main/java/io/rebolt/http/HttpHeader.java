package io.rebolt.http;

import com.google.common.collect.Maps;
import com.google.common.net.MediaType;
import io.rebolt.core.models.IModel;
import io.rebolt.core.utils.HashUtil;
import io.rebolt.core.utils.ObjectUtil;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

import static io.rebolt.http.HttpHeader.Header.Accept;
import static io.rebolt.http.HttpHeader.Header.ContentType;
import static io.rebolt.http.HttpHeader.Header.UserAgent;

@ToString
public final class HttpHeader implements IModel<HttpHeader> {
  private static final long serialVersionUID = 576771757645112134L;
  private @Getter Map<String, String> headers;

  /**
   * HttpHeader 생성 (기본값 포함)
   * <p>
   * User-Agent : Rebolt HttpClient
   * Content-Type : FORM DATA
   * Accept : PLAIN TEXT (UTF-8)
   *
   * @return {@link HttpHeader}
   * @since 0.1.0
   */
  public static HttpHeader create() {
    return new HttpHeader()
        .add(UserAgent, "Rebolt HttpClient")
        .add(ContentType, MediaType.FORM_DATA)
        .add(Accept, MediaType.PLAIN_TEXT_UTF_8);
  }

  private HttpHeader() {
    this.headers = Maps.newHashMap();
  }

  public HttpHeader add(String header, String value) {
    headers.put(header, value);
    return this;
  }

  public HttpHeader add(Header header, MediaType mediaType) {
    return add(header.getHeader(), mediaType.toString());
  }

  public HttpHeader add(String header, MediaType mediaType) {
    return add(header, mediaType.toString());
  }

  public HttpHeader add(Header header, String value) {
    return add(header.getHeader(), value);
  }

  public HttpHeader addAccept(String accept) {
    return add(Header.Accept, accept);
  }

  public HttpHeader addAccept(MediaType meidaType) {
    return add(Header.Accept, meidaType);
  }

  public HttpHeader addContentType(String contentType) {
    return add(Header.ContentType, contentType);
  }

  public HttpHeader addContentType(MediaType mediaType) {
    return add(Header.ContentType, mediaType);
  }

  public String get(String header) {
    return headers.get(header);
  }

  public String get(Header header) {
    return headers.get(header.getHeader());
  }

  public String getAccept() {
    return headers.get(Header.Accept.getHeader());
  }

  public String getContentType() {
    return headers.get(Header.ContentType.getHeader());
  }

  @Override
  public boolean isEmpty() {
    return ObjectUtil.isEmpty(headers);
}

  @Override
  public long deepHash() {
    return HashUtil.deepHash(headers);
  }

  @Override
  public boolean equals(HttpHeader httpHeader) {
    return headers.hashCode() == httpHeader.hashCode();
  }

  /**
   * Header 정의
   */
  public enum Header {
    Accept("Accept"),
    Authorization("Authorization"),
    CacheControl("Cache-Control"),
    ContentType("Content-Type"),
    UserAgent("User-Agent");

    private final @Getter String header;

    Header(String header) {
      this.header = header;
    }

    public static Header lookup(String header) {
      return headerMap.get(header);
    }

    private static final Map<String, Header> headerMap = Maps.newHashMap();

    static {
      for (Header entry : HttpHeader.Header.values()) {
        headerMap.put(entry.getHeader(), entry);
      }
    }
  }
}