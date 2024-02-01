package api.prog5.bookwel.endpoint.rest.mapper;

import api.prog5.bookwel.endpoint.rest.model.BookReaction;
import api.prog5.bookwel.endpoint.rest.model.CrupdateReaction;
import api.prog5.bookwel.service.BookService;
import api.prog5.bookwel.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookReactionMapper {
  private final UserService userService;
  private final BookService bookService;

  public BookReaction toRest(api.prog5.bookwel.model.BookReaction domain) {
    return new BookReaction()
        .bookTitle(domain.getBook().getTitle())
        .id(domain.getId())
        .reactionStatus(domain.getReaction())
        .creationDatetime(domain.getCreationDatetime())
        .reactorName(domain.getReactor().getLastName())
        .reactorId(domain.getReactor().getId());
  }

  public api.prog5.bookwel.model.BookReaction toDomain(CrupdateReaction rest, String bookId) {
    return api.prog5.bookwel.model.BookReaction.builder()
        .book(bookService.getById(bookId))
        .reactor(userService.getById(rest.getReactorId()))
        .reaction(rest.getReactionStatus())
        .build();
  }
}
