package io.rebolt.http.exceptions;

import io.rebolt.core.exceptions.ReboltException;
import io.rebolt.http.HttpStatus;
import lombok.Getter;
import lombok.ToString;
import org.apache.logging.log4j.Level;

@ToString
public final class HttpException extends ReboltException {
  private static final long serialVersionUID = -557421899949901475L;
  protected @Getter final HttpStatus httpStatus;

  public HttpException(int httpStatus) {
    this.httpStatus = HttpStatus.lookup(httpStatus);
    super.setMessage("http_status: " + httpStatus);
  }

  public HttpException(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
    super.setMessage("http_status: " + httpStatus);
  }

  @Override
  protected Level setLogLevel() {
    return Level.WARN;
  }
}
