package api.prog5.bookwel.repository;

import api.prog5.bookwel.endpoint.rest.model.ReactionStatus;
import api.prog5.bookwel.model.Category;
import api.prog5.bookwel.model.CategoryReaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryReactionRepository extends JpaRepository<CategoryReaction, String> {

  List<CategoryReaction> findAllByCategory(Category category);

  List<CategoryReaction> findAllByCategoryAndReaction(
      Category category, ReactionStatus reactionStatus);
}
