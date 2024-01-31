package api.prog5.bookwel.endpoint.rest.mapper;

import api.prog5.bookwel.endpoint.rest.model.BookReaction;
import org.springframework.stereotype.Component;

@Component
public class BookReactionMapper {

  public BookReaction toRest(api.prog5.bookwel.model.BookReaction domain) {
    return new BookReaction()
        .bookTitle(domain.getBook().getTitle())
        .id(domain.getId())
        .reactionStatus(domain.getReaction())
        .creationDatetime(domain.getCreationDatetime())
        .reactorName(domain.getReactor().getLastName())
        .reactorId(domain.getReactor().getId());
  }
}
