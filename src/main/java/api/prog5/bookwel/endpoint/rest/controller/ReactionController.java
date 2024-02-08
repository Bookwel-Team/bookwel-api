package api.prog5.bookwel.endpoint.rest.controller;

import api.prog5.bookwel.endpoint.rest.mapper.BookReactionMapper;
import api.prog5.bookwel.endpoint.rest.mapper.CategoryReactionMapper;
import api.prog5.bookwel.endpoint.rest.model.BookReaction;
import api.prog5.bookwel.endpoint.rest.model.CategoryReaction;
import api.prog5.bookwel.endpoint.rest.model.CrupdateReaction;
import api.prog5.bookwel.endpoint.rest.model.ReactionStatus;
import api.prog5.bookwel.service.BookReactionService;
import api.prog5.bookwel.service.CategoryReactionService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class ReactionController {
  private final BookReactionService bookReactionService;
  private final BookReactionMapper bookReactionMapper;
  private final CategoryReactionService categoryReactionService;
  private final CategoryReactionMapper categoryReactionMapper;

  @GetMapping("/books/{bookId}/reactions")
  public List<BookReaction> getBookReactions(
      @PathVariable String bookId,
      @RequestParam(value = "reaction_status", required = false) ReactionStatus status) {
    return bookReactionService.getReactionsByBook(bookId, status).stream()
        .map(bookReactionMapper::toRest)
        .toList();
  }

  @GetMapping("/categories/{categoryId}/reactions")
  public List<CategoryReaction> getCategoryReactions(
      @PathVariable String categoryId,
      @RequestParam(value = "reaction_status", required = false) ReactionStatus status) {
    return categoryReactionService.getReactionsByCategory(categoryId, status).stream()
        .map(categoryReactionMapper::toRest)
        .toList();
  }

  @PutMapping("/books/{bookId}/reaction")
  public BookReaction crupdateBookReaction(
      @PathVariable String bookId, @RequestBody CrupdateReaction crupdateReaction) {
    return bookReactionMapper.toRest(
        bookReactionService.crupdateBookReaction(
            bookReactionMapper.toDomain(crupdateReaction, bookId)));
  }

  @PutMapping("/categories/{categoryId}/reaction")
  public CategoryReaction crupdateCategoryReaction(
      @PathVariable String categoryId, @RequestBody CrupdateReaction crupdateReaction) {
    return categoryReactionMapper.toRest(
        categoryReactionService.crupdateCategoryReaction(
            categoryReactionMapper.toDomain(crupdateReaction, categoryId)));
  }
}
