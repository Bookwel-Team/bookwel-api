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
import java.util.stream.Collectors;
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

  // TODO: fix reaction status type not match error

  private final BookReactionService bookReactionService;
  private final BookReactionMapper bookReactionMapper;
  private final CategoryReactionService categoryReactionService;
  private final CategoryReactionMapper categoryReactionMapper;

  @GetMapping("/books/{bookId}/reactions")
  public List<BookReaction> getBookReactions(
      @PathVariable String bookId, @RequestParam(value = "reaction_status") String status) {
    return bookReactionService.getReactionByBook(bookId, ReactionStatus.valueOf(status)).stream()
        .map(bookReactionMapper::toRest)
        .collect(Collectors.toUnmodifiableList());
  }

  @GetMapping("/categories/{categoryId}/reactions")
  public List<CategoryReaction> getCategoryReactions(
      @PathVariable String categoryId, @RequestParam(value = "reaction_status") String status) {
    return categoryReactionService
        .getReactionsByCategory(categoryId, ReactionStatus.valueOf(status))
        .stream()
        .map(categoryReactionMapper::toRest)
        .collect(Collectors.toUnmodifiableList());
  }

  @PutMapping("/books/{bookId}/reaction")
  public BookReaction crupdateBookReaction(
      @PathVariable String bookId,
      @RequestBody CrupdateReaction crupdateReaction) {
    return bookReactionMapper.toRest(
        bookReactionService.crupdateBookReaction(crupdateReaction, bookId));
  }

  @PutMapping("/categories/{categoryId}/reaction")
  public CategoryReaction crupdateCategoryReaction(
      @PathVariable String categoryId,
      @RequestBody CrupdateReaction crupdateReaction) {
    return categoryReactionMapper.toRest(
        categoryReactionService.crupdateCategoryReaction(crupdateReaction, categoryId));
  }
}
