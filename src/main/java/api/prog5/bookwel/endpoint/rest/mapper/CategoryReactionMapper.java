package api.prog5.bookwel.endpoint.rest.mapper;

import api.prog5.bookwel.endpoint.rest.model.CategoryReaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CategoryReactionMapper {

  public CategoryReaction toRest(api.prog5.bookwel.model.CategoryReaction domain) {
    return new CategoryReaction()
        .category(domain.getCategory().getName())
        .id(domain.getId())
        .reactionStatus(domain.getReaction())
        .creationDatetime(domain.getCreationDatetime())
        .reactorId(domain.getReactor().getId())
        .reactorName(domain.getReactor().getLastName());
  }
}
