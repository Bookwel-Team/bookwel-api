package api.prog5.bookwel.endpoint.rest.mapper;

import api.prog5.bookwel.endpoint.rest.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
  public Category toRest(api.prog5.bookwel.model.Category domain) {
    return new Category().id(domain.getId()).name(domain.getName());
  }
}
