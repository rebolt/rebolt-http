package io.rebolt.http.fluent;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 복잡한 초기 설정 없이 곧바로 Http 요청을 할 수 있는 인터페이스를 제공한다.
 * <p>
 * 예) ReboltHttp.get().url("http://nexon.com").call();
 * 예) ReboltHttp.post().url("http://api.nexon.com").body(formString).call();
 * 예) ReboltHttp.post().url("http://api.nexon.com").header("Authorization", "...").hedaer("Accept", "...").body(formString).call();
 * 예) ReboltHttp.get().url("http://nexon.com").asyncCall(response -> { ... });
 *
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReboltHttp {

  public static ReboltHttp get() {
    return new ReboltHttp();
  }

  public static ReboltHttp post() {
    return new ReboltHttp();
  }

  public static ReboltHttp delete() {
    return new ReboltHttp();
  }

}
