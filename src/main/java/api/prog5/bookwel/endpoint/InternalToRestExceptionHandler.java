package api.prog5.bookwel.endpoint;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import api.prog5.bookwel.endpoint.rest.exception.BadRequestException;
import api.prog5.bookwel.endpoint.rest.exception.ForbiddenException;
import api.prog5.bookwel.endpoint.rest.exception.NotFoundException;
import api.prog5.bookwel.endpoint.rest.model.Exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class InternalToRestExceptionHandler {

  @ExceptionHandler(value = {BadRequestException.class})
  ResponseEntity<Exception> handleBadRequest(BadRequestException e) {
    log.info("Bad request", e);
    return new ResponseEntity<>(toRest(e, BAD_REQUEST), BAD_REQUEST);
  }

  @ExceptionHandler(value = {DataIntegrityViolationException.class})
  ResponseEntity<Exception> handleDataIntegrityViolation(DataIntegrityViolationException e) {
    log.info("Bad request", e);
    return new ResponseEntity<>(toRest(e, BAD_REQUEST), BAD_REQUEST);
  }

  @ExceptionHandler(value = {MissingServletRequestParameterException.class})
  ResponseEntity<Exception> handleBadRequest(MissingServletRequestParameterException e) {
    log.info("Missing parameter", e);
    return handleBadRequest(new BadRequestException(e.getMessage()));
  }

  @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
  ResponseEntity<Exception> handleBadRequest(HttpRequestMethodNotSupportedException e) {
    log.info("Unsupported method for this endpoint", e);
    return handleBadRequest(new BadRequestException(e.getMessage()));
  }

  @ExceptionHandler(value = {HttpMessageNotReadableException.class})
  ResponseEntity<Exception> handleBadRequest(HttpMessageNotReadableException e) {
    log.info("Missing required body", e);
    return handleBadRequest(new BadRequestException(e.getMessage()));
  }

  @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
  ResponseEntity<Exception> handleConversionFailed(MethodArgumentTypeMismatchException e) {
    log.info("Conversion failed", e);
    String message = e.getCause().getCause().getMessage();
    return handleBadRequest(new BadRequestException(message));
  }

  @ExceptionHandler(value = {java.lang.Exception.class})
  ResponseEntity<api.prog5.bookwel.endpoint.rest.model.Exception> handleDefault(
      java.lang.Exception e) {
    log.error("Internal error", e);
    return new ResponseEntity<>(toRest(e, INTERNAL_SERVER_ERROR), INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(
      value = {
        AccessDeniedException.class,
        ForbiddenException.class,
        AuthenticationException.class
      })
  ResponseEntity<api.prog5.bookwel.endpoint.rest.model.Exception> handleForbidden(
      java.lang.Exception e) {
    /* rest.model.Exception.Type.FORBIDDEN designates both authentication and authorization errors.
     * Hence do _not_ HttpsStatus.UNAUTHORIZED because, counter-intuitively,
     * it's just for authentication.
     * https://stackoverflow.com/questions/3297048/403-forbidden-vs-401-unauthorized-http-responses */
    log.info("Forbidden", e);

    return new ResponseEntity<>(toRest(e, FORBIDDEN), FORBIDDEN);
  }

  @ExceptionHandler(value = {NotFoundException.class})
  ResponseEntity<api.prog5.bookwel.endpoint.rest.model.Exception> handleNotFound(
      NotFoundException e) {
    return new ResponseEntity<>(toRest(e, NOT_FOUND), NOT_FOUND);
  }

  private api.prog5.bookwel.endpoint.rest.model.Exception toRest(
      java.lang.Exception e, HttpStatus status) {
    var restException = new api.prog5.bookwel.endpoint.rest.model.Exception();
    restException.setType(status.toString());
    restException.setMessage(e.getMessage());
    return restException;
  }
}
