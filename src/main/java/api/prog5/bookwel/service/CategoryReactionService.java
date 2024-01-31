package api.prog5.bookwel.service;

import api.prog5.bookwel.endpoint.rest.exception.NotFoundException;
import api.prog5.bookwel.endpoint.rest.model.CrupdateReaction;
import api.prog5.bookwel.endpoint.rest.model.ReactionStatus;
import api.prog5.bookwel.model.Category;
import api.prog5.bookwel.model.CategoryReaction;
import api.prog5.bookwel.model.User;
import api.prog5.bookwel.repository.CategoryReactionRepository;
import api.prog5.bookwel.repository.CategoryRepository;
import api.prog5.bookwel.repository.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryReactionService {

  private final CategoryReactionRepository categoryReactionRepository;
  private final CategoryRepository categoryRepository;
  private final UserRepository userRepository;

  public List<CategoryReaction> getReactionsByCategory(String bookId, ReactionStatus status) {
    Category category =
        categoryRepository
            .findById(bookId)
            .orElseThrow(() -> new NotFoundException("Book not found"));
    return status == null
        ? categoryReactionRepository.findAllByCategory(category)
        : categoryReactionRepository.findAllByCategoryAndReaction(category, status);
  }

  public CategoryReaction crupdateCategoryReaction(CrupdateReaction rest, String bookId) {
    Category category =
        categoryRepository
            .findById(bookId)
            .orElseThrow(() -> new NotFoundException("Book not found"));
    User user =
        userRepository
            .findById(rest.getReactorId())
            .orElseThrow(() -> new NotFoundException("User not found"));
    return categoryReactionRepository.save(
        CategoryReaction.builder()
            .id(rest.getId())
            .category(category)
            .reaction(rest.getReactionStatus())
            .reactor(user)
            .build());
  }
}
