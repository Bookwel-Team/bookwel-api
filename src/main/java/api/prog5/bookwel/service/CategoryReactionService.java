package api.prog5.bookwel.service;

import api.prog5.bookwel.endpoint.rest.model.ReactionStatus;
import api.prog5.bookwel.repository.CategoryReactionRepository;
import api.prog5.bookwel.repository.model.Category;
import api.prog5.bookwel.repository.model.CategoryReaction;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryReactionService {

  private final CategoryReactionRepository categoryReactionRepository;
  private final CategoryService categoryService;

  public List<CategoryReaction> getReactionsByCategory(String bookId, ReactionStatus status) {
    Category category = categoryService.getById(bookId);
    return status == null
        ? categoryReactionRepository.findAllByCategory(category)
        : categoryReactionRepository.findAllByCategoryAndReaction(category, status);
  }

  public CategoryReaction crupdateCategoryReaction(CategoryReaction toSave) {
    return categoryReactionRepository.save(toSave);
  }
}
