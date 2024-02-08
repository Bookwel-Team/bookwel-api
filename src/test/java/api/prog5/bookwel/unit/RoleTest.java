package api.prog5.bookwel.unit;

import static org.junit.jupiter.api.Assertions.assertThrows;

import api.prog5.bookwel.endpoint.rest.security.model.Role;
import org.junit.jupiter.api.Test;

public class RoleTest {

  private static final String UNKNOWN_ROLE = "UNKNOWN_ROLE";

  @Test
  void role_from_value_throws_exception() {
    assertThrows(
        IllegalArgumentException.class,
        () -> Role.fromValue(UNKNOWN_ROLE),
        "Unexpected value '" + UNKNOWN_ROLE + "'");
  }
}
