package api.prog5.bookwel.service.AI.DataProcesser.api.recommendation.mapper;

import api.prog5.bookwel.Generated;
import api.prog5.bookwel.service.AI.DataProcesser.api.recommendation.model.Book;
import org.springframework.stereotype.Component;

@Component("AIBookMapper")
@Generated
public class BookMapper {
  public Book toApiModel(api.prog5.bookwel.repository.model.BookReaction domain, String userId) {
    var domainBook = domain.getBook();
    var result = new Book();
    result.setId(domainBook.getId());
    result.setCategory(domainBook.getCategory());
    result.setAuthor(domainBook.getAuthor());
    result.setTitle(domainBook.getTitle());
    result.setFilename(domainBook.getFilename());
    result.setUserId(userId);
    result.setUserReaction(domain.getReaction());
    return result;
  }

  public api.prog5.bookwel.repository.model.Book toDomain(Book apiModel) {
    return apiModel;
  }
}
