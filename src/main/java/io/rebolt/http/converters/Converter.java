package io.rebolt.http.converters;

/**
 * Http 프로토콜위에 페이로드되는 사용자 프로토콜 컨버터
 * <p>
 * {@link Converter}에서 제공하는 인터페이스 스펙을 구현하면
 * 사용자 프로토콜 컨버터를 손쉽게 추가해서 사용할 수 있다.
 *
 * @since 1.0
 */
public interface Converter<T> {
  T convert(Object message);
}
