package api.prog5.bookwel.repository;

import api.prog5.bookwel.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
   List<Category> findAllByName(String name);
}
