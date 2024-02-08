package api.prog5.bookwel.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import api.prog5.bookwel.endpoint.rest.controller.HealthController;
import api.prog5.bookwel.integration.mocks.CustomFacadeIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HealthControllerIT extends CustomFacadeIT {
  @Autowired HealthController subject;

  @Test
  void hello() {
    assertEquals("Hello from the Bookwel Team!", subject.checkHealth());
  }

  @Test
  void client_hello() {
    assertEquals("Hello from a client", subject.sayHello());
  }
}
