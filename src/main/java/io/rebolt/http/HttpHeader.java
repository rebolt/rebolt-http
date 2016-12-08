package io.rebolt.http;

import com.google.common.collect.Maps;
import com.google.common.net.MediaType;
import io.rebolt.core.models.IModel;
import io.rebolt.core.utils.HashUtil;
import io.rebolt.core.utils.ObjectUtil;
import io.rebolt.core.utils.StringUtil;
import io.rebolt.http.converters.Converter;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

import static io.rebolt.http.HttpHeader.Header.Accept;
import static io.rebolt.http.HttpHeader.Header.UserAgent;

@ToString
public final class HttpHeader implements IModel<HttpHeader> {
  private static final long serialVersionUID = 576771757645112134L;
  private static final String userAgent = "Rebolt-Http/1.0";
  private @Getter Map<String, String> headerMap;

  /**
   * HttpHeader 생성 (기본값 포함, 요청용)
   * <p>
   * User-Agent : Rebolt-Http/(version) (RFC 7231: https://tools.ietf.org/html/rfc7231#section-5.5.3)
   * Content-Type : FORM DATA
   * Accept : PLAIN TEXT (UTF-8)
   * <p>
   *
   * @return {@link HttpHeader}
   * @since 1.0
   */
  public static HttpHeader create() {
    return new HttpHeader()
        .add(UserAgent, userAgent)
        .add(Accept, MediaType.PLAIN_TEXT_UTF_8);
  }

  /**
   * HttpHeader 생성 (with {@link Converter}
   *
   * @param converter {@link Converter}
   * @return {@link HttpHeader}
   * @since 1.0
   */
  public static HttpHeader create(Converter converter) {
    return new HttpHeader()
        .add(UserAgent, userAgent)
        .addAccept(converter.getAccept())
        .addContentType(converter.getContentType());
  }

  /**
   * HttpHeader 생성 (기본값 없음, 응답용)
   *
   * @return {@link HttpHeader}
   * @since 1.0
   */
  public static HttpHeader createForResponse() {
    return new HttpHeader();
  }

  private HttpHeader() {
    this.headerMap = Maps.newHashMap();
  }

  public HttpHeader add(String header, String value) {
    headerMap.put(header, value);
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

  public HttpHeader add(String header, List<String> values) {
    headerMap.put(header, StringUtil.join("; ", values));
    return this;
  }

  public HttpHeader addAll(HttpHeader httpHeader) {
    headerMap.putAll(httpHeader.getHeaderMap());
    return this;
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
    return headerMap.get(header);
  }

  public String get(Header header) {
    return headerMap.get(header.getHeader());
  }

  public String getAccept() {
    return headerMap.get(Header.Accept.getHeader());
  }

  public String getContentType() {
    return headerMap.get(Header.ContentType.getHeader());
  }

  @Override
  public boolean isEmpty() {
    return ObjectUtil.isEmpty(headerMap);
  }

  @Override
  public long deepHash() {
    return HashUtil.deepHash(headerMap);
  }

  @Override
  public boolean equals(HttpHeader httpHeader) {
    return headerMap.hashCode() == httpHeader.hashCode();
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
      return _headerMap.get(header);
    }

    private static final Map<String, Header> _headerMap = Maps.newHashMap();

    static {
      for (Header entry : HttpHeader.Header.values()) {
        _headerMap.put(entry.getHeader(), entry);
      }
    }
  }
}