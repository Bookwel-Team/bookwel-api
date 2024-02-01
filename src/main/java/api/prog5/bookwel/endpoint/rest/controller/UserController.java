package api.prog5.bookwel.endpoint.rest.controller;

import api.prog5.bookwel.model.User;
import api.prog5.bookwel.service.UserService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/users")
  public List<User> getAllUsers() {
    return userService.getAll();
  }

  @GetMapping("/users/{id}")
  public User getUserById(@PathVariable String id) {
    return userService.getById(id);
  }
}
