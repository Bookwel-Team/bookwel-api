package api.prog5.bookwel.endpoint.rest.mapper;

import api.prog5.bookwel.endpoint.rest.model.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookMapper {

  public Book toRest(api.prog5.bookwel.repository.model.Book domain) {
    return new Book()
        .id(domain.getId())
        .author(domain.getAuthor())
        .fileLink(domain.getFileLink())
        .category(domain.getCategory().getName())
        .title(domain.getTitle());
  }
}
