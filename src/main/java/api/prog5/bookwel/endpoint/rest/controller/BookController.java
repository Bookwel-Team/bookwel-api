package api.prog5.bookwel.endpoint.rest.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import api.prog5.bookwel.endpoint.rest.mapper.BookMapper;
import api.prog5.bookwel.endpoint.rest.model.Book;
import api.prog5.bookwel.endpoint.rest.security.model.Principal;
import api.prog5.bookwel.service.BookService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
      @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
      @RequestParam(required = false) String userId) {
    return bookService.getAllByCriteria(author, title, category, page, pageSize).stream()
        .map(b -> bookMapper.toRest(b, userId))
        .toList();
  }

  @GetMapping("/recommended-books")
  public List<Book> getRecommendedBooks(@AuthenticationPrincipal Principal principal) {
    return bookService.getAllRecommendedBooksFor(principal.getUser().getId()).stream()
        .map(b -> bookMapper.toRest(b, principal.getUser().getId()))
        .toList();
  }

  @GetMapping("/books/{id}")
  public Book getBookById(@PathVariable String id, @AuthenticationPrincipal Principal principal) {
    return bookMapper.toRest(bookService.getById(id), principal.getUser().getId());
  }

  @PostMapping(value = "/books", consumes = MULTIPART_FORM_DATA_VALUE)
  public Book uploadNewBook(
      @RequestParam("category") String category,
      @RequestParam("book") MultipartFile bookAsMultipartFile,
      @AuthenticationPrincipal Principal principal) {
    return bookMapper.toRest(
        bookService.uploadNewBook(bookAsMultipartFile, category), principal.getUser().getId());
  }
}
