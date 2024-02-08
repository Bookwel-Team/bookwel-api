package api.prog5.bookwel.endpoint;

import api.prog5.bookwel.endpoint.rest.exception.ForbiddenException;
import api.prog5.bookwel.endpoint.rest.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
public class InternalToRestExceptionHandler {

  @ExceptionHandler(value = {Exception.class})
  ResponseEntity<api.prog5.bookwel.endpoint.rest.model.Exception> handleDefault(Exception e) {
    log.error("Internal error", e);
    return new ResponseEntity<>(
        toRest(e, INTERNAL_SERVER_ERROR), INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(
      value = {
        AccessDeniedException.class,
        ForbiddenException.class,
        AuthenticationException.class
      })
  ResponseEntity<api.prog5.bookwel.endpoint.rest.model.Exception> handleForbidden(Exception e) {
    /* rest.model.Exception.Type.FORBIDDEN designates both authentication and authorization errors.
     * Hence do _not_ HttpsStatus.UNAUTHORIZED because, counter-intuitively,
     * it's just for authentication.
     * https://stackoverflow.com/questions/3297048/403-forbidden-vs-401-unauthorized-http-responses */
    log.info("Forbidden", e);

    return new ResponseEntity<>(toRest(e, HttpStatus.FORBIDDEN), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(value = {NotFoundException.class})
  ResponseEntity<api.prog5.bookwel.endpoint.rest.model.Exception> handleNotFound(Exception e) {
    return new ResponseEntity<>(toRest(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
  }

  private api.prog5.bookwel.endpoint.rest.model.Exception toRest(
      java.lang.Exception e, HttpStatus status) {
    var restException = new api.prog5.bookwel.endpoint.rest.model.Exception();
    restException.setType(status.toString());
    restException.setMessage(e.getMessage());
    return restException;
  }
}
