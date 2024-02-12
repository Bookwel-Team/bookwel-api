package api.prog5.bookwel.endpoint.rest.controller;

import api.prog5.bookwel.endpoint.rest.mapper.BookReactionMapper;
import api.prog5.bookwel.endpoint.rest.mapper.CategoryReactionMapper;
import api.prog5.bookwel.endpoint.rest.model.BookReaction;
import api.prog5.bookwel.endpoint.rest.model.CategoryReaction;
import api.prog5.bookwel.endpoint.rest.model.CategoryCrupdateReaction;
import api.prog5.bookwel.endpoint.rest.model.CrupdateReaction;
import api.prog5.bookwel.service.BookReactionService;
import api.prog5.bookwel.service.CategoryReactionService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class ReactionController {
  private final BookReactionService bookReactionService;
  private final BookReactionMapper bookReactionMapper;
  private final CategoryReactionService categoryReactionService;
  private final CategoryReactionMapper categoryReactionMapper;

  @PutMapping("/books/{bookId}/reaction")
  public BookReaction crupdateBookReaction(
      @PathVariable String bookId, @RequestBody CrupdateReaction crupdateReaction) {
    return bookReactionMapper.toRest(
        bookReactionService.crupdateBookReaction(
            bookReactionMapper.toDomain(crupdateReaction, bookId)));
  }

  @PutMapping("/categories/reactions")
  public List<CategoryReaction> crupdateCategoryReaction(@RequestBody List<CategoryCrupdateReaction> crupdateReactions) {
    var mappedToDomain = crupdateReactions.stream().map(categoryReactionMapper::toDomain).toList();
    return
        categoryReactionService.crupdateCategoryReactions(mappedToDomain).stream().map(categoryReactionMapper::toRest).toList();
  }
}
