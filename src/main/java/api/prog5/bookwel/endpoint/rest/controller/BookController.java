package api.prog5.bookwel.endpoint.rest.controller;

import api.prog5.bookwel.endpoint.rest.mapper.BookMapper;
import api.prog5.bookwel.endpoint.rest.model.Book;
import api.prog5.bookwel.endpoint.rest.security.model.Principal;
import api.prog5.bookwel.service.BookService;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
public class BookController {

  private final BookService bookService;
  private final BookMapper bookMapper;

  @GetMapping("/books")
  public List<Book> getBooks(
      @RequestParam(value = "author", required = false) String author,
      @RequestParam(value = "category", required = false) String category,
      @RequestParam(value = "title", required = false) String title,
      @RequestParam(value = "page", defaultValue = "1") Integer page,
      @RequestParam(value = "page_size", defaultValue = "20") Integer pageSize,
      @AuthenticationPrincipal
      Principal principal) {
    //added currentUser check because endpoint is permitAll so anonymous users have UNSET reaction
    var currentUser = principal == null ? null : principal.getUser();
    return bookService.getAllByCriteria(author, title, category, page, pageSize).stream()
        .map(b -> bookMapper.toRest(b, currentUser))
        .toList();
  }

  @GetMapping("/recommended-books")
  public List<Book> getRecommendedBooks(@AuthenticationPrincipal Principal principal) {
    return bookService.getAllRecommendedBooksFor(principal.getUser().getId()).stream()
        .map(b -> bookMapper.toRest(b, null))
        .toList();
  }

  @GetMapping("/books/{id}")
  public Book getBookById(@PathVariable String id, @AuthenticationPrincipal Principal principal) {
    return bookMapper.toRest(bookService.getById(id), principal.getUser());
  }

  @PutMapping(
      value = "/books",
      consumes = {"multipart/form-data"})
  public Book crupdateBook(
      @RequestParam(name = "title") String title,
      @RequestParam(name = "author") String author,
      @RequestParam(name = "category") String category,
      @RequestParam(name = "book") MultipartFile book,
      @AuthenticationPrincipal Principal principal)
      throws IOException {
    return bookMapper.toRest(bookService.crupdateBook(book, title ,author, category), principal.getUser());
  }
}
