package io.rebolt.http.exceptions;

import io.rebolt.core.exceptions.ReboltException;
import io.rebolt.core.utils.ObjectUtil;
import io.rebolt.http.HttpStatus;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.event.Level;

import static io.rebolt.core.constants.Constants.STRING_EMPTY;

@ToString
public final class HttpException extends ReboltException {
  private static final long serialVersionUID = -557421899949901475L;
  private @Getter final HttpStatus status;
  private @Getter final String message;

  public HttpException(int status) {
    this(HttpStatus.lookup(status), STRING_EMPTY);
  }

  public HttpException(int status, String message) {
    this(HttpStatus.lookup(status), message);
  }

  public HttpException(HttpStatus status) {
    this(status, STRING_EMPTY);
  }

  public HttpException(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
    super.setMessage("http_status: " + status + ", " + message);
  }

  public boolean hasError() {
    return ObjectUtil.isNull(status) || status.hasError();
  }

  @Override
  protected Level setLogLevel() {
    return Level.WARN;
  }
}
