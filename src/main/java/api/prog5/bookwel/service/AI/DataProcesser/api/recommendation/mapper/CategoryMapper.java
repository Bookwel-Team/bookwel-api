package api.prog5.bookwel.service.AI.DataProcesser.api.recommendation.mapper;

import api.prog5.bookwel.Generated;
import api.prog5.bookwel.repository.model.CategoryReaction;
import api.prog5.bookwel.service.AI.DataProcesser.api.recommendation.model.Book;
import api.prog5.bookwel.service.AI.DataProcesser.api.recommendation.model.Category;
import org.springframework.stereotype.Component;

@Component("AICategoryMapper")
@Generated
public class CategoryMapper {
  public Category toApiModel(CategoryReaction domain, String userId) {
    var domainCategory = domain.getCategory();
    var result = new Category();
    result.setId(domainCategory.getId());
    result.setName(domainCategory.getName());
    result.setUserId(userId);
    result.setUserReaction(domain.getReaction());
    return result;
  }

  public api.prog5.bookwel.repository.model.Book toDomain(Book apiModel) {
    return apiModel;
  }
}
