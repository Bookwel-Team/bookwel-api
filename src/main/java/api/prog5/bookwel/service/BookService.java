package api.prog5.bookwel.service;

import static org.springframework.data.domain.Sort.Direction.DESC;

import api.prog5.bookwel.endpoint.rest.exception.NotFoundException;
import api.prog5.bookwel.file.BucketComponent;
import api.prog5.bookwel.repository.BookRepository;
import api.prog5.bookwel.repository.dao.BookDao;
import api.prog5.bookwel.repository.model.Book;
import api.prog5.bookwel.repository.model.BookReaction;
import api.prog5.bookwel.service.AI.DataProcesser.BookUserSpecificDataProcesser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class BookService {
  private final BookDao dao;
  private final BookRepository repository;
  private final BucketComponent bucketComponent;
  private final BookUserSpecificDataProcesser dataProcesser;
  private final BookReactionService reactionService;
  private final CategoryReactionService categoryReactionService;

  public List<Book> getAllByCriteria(
      String author, String title, String category, Integer page, Integer pageSize) {
    Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(DESC, "author"));
    return dao.findByCriteria(author, title, category, pageable);
  }

  public List<Book> getAllRecommendedBooksFor(String userId) {
    List<BookReaction> reactedBooks = reactionService.getAllBy(userId);
    return dataProcesser.process(reactedBooks, repository.findAll(), categoryReactionService.getAllBy(userId), userId);
  }

  public Book getById(String id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Book with id: " + id + " not found"));
  }

  public Book crupdateBook(MultipartFile bookAsFile) throws IOException {
    String dir = "/tmp";
    Path filepath = Paths.get(dir, bookAsFile.getOriginalFilename());
    bookAsFile.transferTo(filepath);
    File file = new File(filepath.toUri());
    String filename = file.toString().substring(5);
    bucketComponent.upload(file, file.getName());
    return repository.save(Book.builder().filename(filename).build());
  }

  public URL getPresignedUrlFromFilename(String filename) {
    return bucketComponent.presign(filename, Duration.ofMinutes(10));
  }
}
