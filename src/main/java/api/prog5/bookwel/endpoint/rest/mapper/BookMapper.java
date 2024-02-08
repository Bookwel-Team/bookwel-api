package api.prog5.bookwel.endpoint.rest.mapper;

import api.prog5.bookwel.endpoint.rest.model.Book;
import api.prog5.bookwel.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookMapper {
  private BookService bookService;

  public Book toRest(api.prog5.bookwel.repository.model.Book domain) {
    String filename = domain.getFilename();
    return new Book()
        .id(domain.getId())
        .author(domain.getAuthor())
        .fileName(filename)
        .fileLink(bookService.getPresignedUrlFromFilename(filename).toString())
        .category(domain.getCategory() != null ? domain.getCategory().getName() : null)
        .title(domain.getTitle());
  }
}
