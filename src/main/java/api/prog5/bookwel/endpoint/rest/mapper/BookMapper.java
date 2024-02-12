package api.prog5.bookwel.endpoint.rest.mapper;

import static api.prog5.bookwel.endpoint.rest.model.ReactionStatus.DISLIKE;
import static api.prog5.bookwel.endpoint.rest.model.ReactionStatus.LIKE;
import static api.prog5.bookwel.endpoint.rest.model.ReactionStatus.UNSET;

import api.prog5.bookwel.endpoint.rest.model.Book;
import api.prog5.bookwel.endpoint.rest.model.ReactionStatistics;
import api.prog5.bookwel.repository.model.User;
import api.prog5.bookwel.service.BookReactionService;
import api.prog5.bookwel.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookMapper {
  private final BookService service;
  private final BookReactionService reactionService;

  public Book toRest(api.prog5.bookwel.repository.model.Book domain, User currentUser) {
    String filename = domain.getFilename();
    return new Book()
        .id(domain.getId())
        .author(domain.getAuthor())
        .fileName(filename)
        .fileLink(service.getPresignedUrlFromFilename(filename).toString())
        .category(domain.getCategory().getName())
        .title(domain.getTitle())
        .reactionStatistics(
            new ReactionStatistics()
                .byCurrentUser(
                    currentUser == null
                        ? UNSET
                        : reactionService.getReactionStatusBy(domain.getId(), currentUser.getId()))
                .dislikeNumbers(reactionService.countAllBy(domain.getId(), LIKE))
                .likeNumbers(reactionService.countAllBy(domain.getId(), DISLIKE)));
  }
}
