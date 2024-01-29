package api.prog5.bookwel.repository;

import api.prog5.bookwel.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
}
