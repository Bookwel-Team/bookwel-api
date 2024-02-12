package api.prog5.bookwel.repository;

import api.prog5.bookwel.endpoint.rest.model.ReactionStatus;
import api.prog5.bookwel.repository.model.Category;
import api.prog5.bookwel.repository.model.CategoryReaction;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryReactionRepository extends JpaRepository<CategoryReaction, String> {

  List<CategoryReaction> findAllByCategory(Category category);

  List<CategoryReaction> findAllByCategoryAndReaction(
      Category category, ReactionStatus reactionStatus);

  Optional<CategoryReaction> findByCategoryIdAndReactorId(String categoryId, String reactorId);
  List<CategoryReaction> findAllByReactorId(String reactorId);
  int countAllByCategoryIdAndReaction(String categoryId, ReactionStatus reaction);
}
