package api.prog5.bookwel.service;

import api.prog5.bookwel.repository.UserRepository;
import api.prog5.bookwel.repository.model.User;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository repository;

  public List<User> getAll() {
    return repository.findAll();
  }

  public User getById(String id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("User.Id = " + id + " not found."));
  }

  public User getByEmail(String email) {
    return repository
        .findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User.Email = " + email + " not found."));
  }

  public User save(User user) {
    return repository.save(user);
  }
}
