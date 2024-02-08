package api.prog5.bookwel.endpoint.rest.exception;

public class TooManyRequestsException extends ApiException {
  public TooManyRequestsException(String message) {
    super(ExceptionType.CLIENT_EXCEPTION, message);
  }
}
