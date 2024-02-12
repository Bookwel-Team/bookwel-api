package api.prog5.bookwel.endpoint.rest.mapper;

import static api.prog5.bookwel.endpoint.rest.model.ReactionStatus.DISLIKE;
import static api.prog5.bookwel.endpoint.rest.model.ReactionStatus.LIKE;
import static api.prog5.bookwel.endpoint.rest.model.ReactionStatus.UNSET;

import api.prog5.bookwel.endpoint.rest.model.Category;
import api.prog5.bookwel.endpoint.rest.model.ReactionStatistics;
import api.prog5.bookwel.repository.model.User;
import api.prog5.bookwel.service.CategoryReactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CategoryMapper {
  private final CategoryReactionService reactionService;

  public Category toRest(api.prog5.bookwel.repository.model.Category domain, User user) {
    return new Category()
        .id(domain.getId())
        .name(domain.getName())
        .reactionStatistics(
            new ReactionStatistics()
                .likeNumbers(reactionService.countAllBy(domain.getId(), LIKE))
                .dislikeNumbers(reactionService.countAllBy(domain.getId(), DISLIKE))
                .byCurrentUser(
                    user == null
                        ? UNSET
                        : reactionService.getReactionStatusBy(domain.getId(), user.getId())));
  }
}
