package api.prog5.bookwel.endpoint.rest.exception;

import static api.prog5.bookwel.endpoint.rest.exception.ApiException.ExceptionType.CLIENT_EXCEPTION;

public class ForbiddenException extends ApiException {
  public ForbiddenException(String message) {
    super(CLIENT_EXCEPTION, message);
  }
}
