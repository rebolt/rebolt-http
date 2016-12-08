package io.rebolt.http.factories;

import io.rebolt.core.exceptions.NotInitializedException;
import io.rebolt.core.utils.ObjectUtil;
import io.rebolt.http.HttpStatus;
import io.rebolt.http.converters.Converter;
import io.rebolt.http.converters.ConverterTable;
import io.rebolt.http.engines.AbstractEngine;

/**
 * 클라이언트 팩토리
 *
 * @since 1.0
 */
public abstract class AbstractFactory {

  protected AbstractEngine engine;

  /**
   * 통신엔진 설정
   *
   * @param engine {@link AbstractEngine}
   */
  protected void setEngine(AbstractEngine engine) {
    ObjectUtil.requireNonNull(engine);
    this.engine = engine;
  }

  /**
   * 커넥션 타임아웃 설정
   *
   * @param connectionTimeout 커넥션 타임아웃 (단위: millisecond)
   * @since 1.0
   */
  public void setConnectionTimeout(int connectionTimeout) {
    ObjectUtil.requireNonNull(engine);
    if (connectionTimeout < 200) {
      throw new NotInitializedException("connectionTimeout must be greater than 200 milliseconds");
    }
    engine.setConnectionTimeout(connectionTimeout);
  }

  /**
   * 수신 타임아웃 설정
   *
   * @param readTimeout 수신 타임아웃 설정 (단위: millisecond)
   * @since 1.0
   */
  public void setReadTimeout(int readTimeout) {
    ObjectUtil.requireNonNull(engine);
    if (readTimeout < 200) {
      throw new NotInitializedException("readTimeout must be greater than 200 milliseconds");
    }
    engine.setReadTimeout(readTimeout);
  }

  /**
   * 송신 타임아웃 설정
   *
   * @param writeTimeout 송신 타임아웃 (단위: millisecond_
   * @since 1.0
   */
  public void setWriteTimeout(int writeTimeout) {
    ObjectUtil.requireNonNull(engine);
    if (writeTimeout < 200) {
      throw new NotInitializedException("writeTimeout must be greater than 200 milliseconds");
    }
    engine.setWriteTimeout(writeTimeout);
  }

  /**
   * 요청당 재시도 횟수
   *
   * @param retryCount 재시도 횟수
   * @since 1.0
   */
  public void setRetryCount(int retryCount) {
    ObjectUtil.requireNonNull(engine);
    if (retryCount < 0) {
      retryCount = 0;
    }
    engine.setRetryCount(retryCount);
  }

  /**
   * 재시도 조건 추가
   *
   * @param httpStatuses {@link HttpStatus}
   * @since 1.0
   */
  public void addRetryStatus(HttpStatus... httpStatuses) {
    ObjectUtil.requireNonNull((Object[]) httpStatuses);
    engine.addRetryStatus(httpStatuses);
  }

  /**
   * {@link Converter} 추가
   *
   * @param requestType 요청 페이로드 클래스
   * @param responseType 응답 페이로드 클래스
   * @param converterType {@link Converter} 클래스
   * @since 1.0
   */
  public void addConverter(Class<?> requestType, Class<?> responseType, Class<? extends Converter> converterType) {
    ObjectUtil.isOrNull(requestType, responseType, converterType);
    ConverterTable.add(requestType, responseType, converterType);
  }

}
