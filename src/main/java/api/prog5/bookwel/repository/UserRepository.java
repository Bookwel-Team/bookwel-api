package api.prog5.bookwel.repository;

import api.prog5.bookwel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  User findByEmail(String email);
}
