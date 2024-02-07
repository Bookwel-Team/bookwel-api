package api.prog5.bookwel.service;

import static org.springframework.data.domain.Sort.Direction.DESC;

import api.prog5.bookwel.endpoint.rest.exception.NotFoundException;
import api.prog5.bookwel.file.BucketComponent;
import api.prog5.bookwel.repository.BookRepository;
import api.prog5.bookwel.repository.dao.BookDao;
import api.prog5.bookwel.repository.model.Book;

import java.net.URL;
import java.time.Duration;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookService {
  private final BookDao bookDao;
  private final BookRepository bookRepository;
  private final BucketComponent bucketComponent;

  public List<Book> getAllByCriteria(
      String author, String category, Integer page, Integer pageSize) {
    Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(DESC, "author"));
    return bookDao.findByCriteria(author, category, pageable);
  }

  public Book getById(String id) {
    return bookRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Book with id: " + id + " not found"));
  }

  public URL getPresignedUrlFromFilename(String filename){
    return bucketComponent.presign(filename, Duration.ofMinutes(2));
  }
}
