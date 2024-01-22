package api.prog5.bookwel.integration;


import api.prog5.bookwel.conf.FacadeIT;
import api.prog5.bookwel.endpoint.controller.HealthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class HealthControllerIT extends FacadeIT {
  @Autowired
  HealthController subject;

  @Test
  public void hello() {
    assertEquals("Hello from the Bookwel Team!", subject.checkHealth());
  }
}
