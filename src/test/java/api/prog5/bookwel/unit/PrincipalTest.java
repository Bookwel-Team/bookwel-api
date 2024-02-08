package api.prog5.bookwel.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import api.prog5.bookwel.endpoint.rest.security.model.Principal;
import api.prog5.bookwel.repository.model.User;
import org.junit.jupiter.api.Test;

public class PrincipalTest {
  public static final String BEARER = "BEARER";
  Principal subject = new Principal(new User(), BEARER);

  // Apparently subject.getPassword is never really tested.
  @Test
  void subject_get_password_ok() {
    assertEquals(BEARER, subject.getPassword());
  }
}
