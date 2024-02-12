package api.prog5.bookwel.service;

import static api.prog5.bookwel.endpoint.rest.model.ReactionStatus.UNSET;

import api.prog5.bookwel.endpoint.rest.model.ReactionStatus;
import api.prog5.bookwel.repository.CategoryReactionRepository;
import api.prog5.bookwel.repository.model.BookReaction;
import api.prog5.bookwel.repository.model.Category;
import api.prog5.bookwel.repository.model.CategoryReaction;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryReactionService {

  private final CategoryReactionRepository repository;

  public List<CategoryReaction> crupdateCategoryReactions(List<CategoryReaction> reactions){
      return reactions.stream().map(this::crupdateCategoryReaction).toList();
  }
  public CategoryReaction crupdateCategoryReaction(CategoryReaction toSave) {
    Optional<CategoryReaction> optionalExistingReaction =
        repository.findByCategoryIdAndReactorId(
            toSave.getCategory().getId(), toSave.getReactor().getId());
    return repository.save(
        optionalExistingReaction
            .map(
                cr -> {
                  cr.setReaction(toSave.getReaction());
                  return cr;
                })
            .orElse(toSave));
  }

  public List<CategoryReaction> getAllBy(String reactorId){
    return repository.findAllByReactorId(reactorId);
  }
  public int countAllBy(String categoryId, ReactionStatus status){
    return repository.countAllByCategoryIdAndReaction(categoryId, status);
  }

  public ReactionStatus getReactionStatusBy(String categoryId, String reactorId){
    return repository.findByCategoryIdAndReactorId(categoryId, reactorId).map(CategoryReaction::getReaction).orElse(UNSET);
  }
}
