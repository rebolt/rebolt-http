package io.rebolt.http;

import com.google.common.collect.Maps;
import io.rebolt.core.exceptions.NotSupportedException;
import io.rebolt.core.utils.StringUtil;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@ToString
public enum HttpMethod {

  Get("get"),
  Post("post"),
  Delete("delete"),
  Put("put"),
  Patch("patch"),
  Head("head");

  private final @Getter String method;

  HttpMethod(String method) {
    this.method = method;
  }

  public static HttpMethod lookup(String method) {
    if (StringUtil.isNullOrEmpty(method)) {
      return Get;
    }
    HttpMethod httpMethod = methodMap.get(method.toLowerCase());
    if (httpMethod == null) {
      throw new NotSupportedException("not supported method: " + method);
    }
    return httpMethod;
  }

  public boolean equals(HttpMethod httpMethod) {
    return httpMethod != null && httpMethod.getMethod().equals(method);
  }

  private static Map<String, HttpMethod> methodMap = Maps.newHashMap();

  static {
    for (HttpMethod entry : HttpMethod.values()) {
      methodMap.put(entry.getMethod(), entry);
    }
  }

}