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

@RestControllerAdvice
@Slf4j
public class InternalToRestExceptionHandler {

  @ExceptionHandler(value = {Exception.class})
  ResponseEntity<Exception> handleDefault(Exception e) {
    log.error("Internal error", e);
    return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(
      value = {
        AccessDeniedException.class,
        ForbiddenException.class,
        AuthenticationException.class
      })
  ResponseEntity<Exception> handleForbidden(Exception e) {
    /* rest.model.Exception.Type.FORBIDDEN designates both authentication and authorization errors.
     * Hence do _not_ HttpsStatus.UNAUTHORIZED because, counter-intuitively,
     * it's just for authentication.
     * https://stackoverflow.com/questions/3297048/403-forbidden-vs-401-unauthorized-http-responses */
    log.info("Forbidden", e);

    return new ResponseEntity<>(e, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(value = {NotFoundException.class})
  ResponseEntity<Exception> handleNotFound(Exception e) {
    return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
  }
}
