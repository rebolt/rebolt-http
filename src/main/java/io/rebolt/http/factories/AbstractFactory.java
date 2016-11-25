package io.rebolt.http.factories;

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
    this.engine = engine;
  }

  /**
   * 커넥션 타임아웃 설정
   *
   * @since 1.0
   */
  public void setConnectionTimeout(int connectionTimeout) {
    ObjectUtil.requireNonNull(engine);
    engine.setConnectionTimeout(connectionTimeout);
  }

  /**
   * 타임아웃 설정
   *
   * @since 1.0
   */
  public void setReadTimeout(int readTimeout) {
    ObjectUtil.requireNonNull(engine);
    engine.setReadTimeout(readTimeout);
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
