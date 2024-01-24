package api.prog5.bookwel.service;

import api.prog5.bookwel.model.User;
import api.prog5.bookwel.repository.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }
  public User getUserById(String id) {
    return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User.Id = " + id + " not found."));
  }

  public User getUserByEmail(String email){
    return userRepository.findByEmail(email);
  }
}
