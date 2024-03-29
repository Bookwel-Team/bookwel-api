package api.prog5.bookwel.endpoint.rest.mapper;

import api.prog5.bookwel.endpoint.rest.model.CategoryCrupdateReaction;
import api.prog5.bookwel.endpoint.rest.model.CategoryReaction;
import api.prog5.bookwel.service.CategoryService;
import api.prog5.bookwel.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CategoryReactionMapper {
  private final CategoryService categoryService;
  private final UserService userService;

  public CategoryReaction toRest(api.prog5.bookwel.repository.model.CategoryReaction domain) {
    return new CategoryReaction()
        .category(domain.getCategory().getName())
        .id(domain.getId())
        .reactionStatus(domain.getReaction())
        .creationDatetime(domain.getCreationDatetime())
        .reactorId(domain.getReactor().getId())
        .reactorName(domain.getReactor().getLastName());
  }

  public api.prog5.bookwel.repository.model.CategoryReaction toDomain(
      CategoryCrupdateReaction rest) {
    return api.prog5.bookwel.repository.model.CategoryReaction.builder()
        .reaction(rest.getReactionStatus())
        .reactor(userService.getById(rest.getReactorId()))
        .category(categoryService.getById(rest.getCategoryId()))
        .build();
  }
}
