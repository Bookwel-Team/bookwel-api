package api.prog5.bookwel.endpoint.rest.exception;

public class ForbiddenException extends ApiException {
  public ForbiddenException(String message) {
    super(ExceptionType.CLIENT_EXCEPTION, message);
  }

  public ForbiddenException() {
    super(ExceptionType.CLIENT_EXCEPTION, "Access is denied");
  }
}
