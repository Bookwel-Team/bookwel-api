package api.prog5.bookwel.repository;

import api.prog5.bookwel.repository.model.Category;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
  List<Category> findAllByName(String name);
  Optional<Category> findByName(String name);
}
