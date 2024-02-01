package api.prog5.bookwel.endpoint.rest.controller;

import api.prog5.bookwel.endpoint.rest.mapper.BookMapper;
import api.prog5.bookwel.endpoint.rest.model.Book;
import api.prog5.bookwel.service.BookService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BookController {

  private final BookService bookService;
  private final BookMapper bookMapper;

  @GetMapping("/books")
  public List<Book> getBooks(
      @RequestParam(value = "author", required = false) String author,
      @RequestParam(value = "category", required = false) String category,
      @RequestParam(value = "page", defaultValue = "1") Integer page,
      @RequestParam(value = "page_size", defaultValue = "20") Integer pageSize) {
    return bookService.getBooksByCriteria(author, category, page, pageSize).stream()
        .map(bookMapper::toRest)
        .toList();
  }

  @GetMapping("/books/{id}")
  public Book getBookById(@PathVariable String id) {
    return bookMapper.toRest(bookService.getBookById(id));
  }
}
