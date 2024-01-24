package api.prog5.bookwel.endpoint.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
  @GetMapping("/hello")
  public String checkHealth() {
    return "Hello from the Bookwel Team!";
  }

  @GetMapping("/client")
  public String sayHello() {
    return "Hello from a client";
  }
}
