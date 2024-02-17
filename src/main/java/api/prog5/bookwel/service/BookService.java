package api.prog5.bookwel.service;

import static org.springframework.data.domain.Sort.Direction.DESC;

import api.prog5.bookwel.endpoint.rest.exception.NotFoundException;
import api.prog5.bookwel.file.BucketComponent;
import api.prog5.bookwel.repository.BookRepository;
import api.prog5.bookwel.repository.dao.BookDao;
import api.prog5.bookwel.repository.model.Book;
import api.prog5.bookwel.repository.model.BookReaction;
import api.prog5.bookwel.repository.model.Category;
import api.prog5.bookwel.service.AI.DataProcesser.BookUserSpecificDataProcesser;
import api.prog5.bookwel.service.AI.DataProcesser.api.pdfReading.PdfReadingAPI;
import api.prog5.bookwel.service.AI.DataProcesser.api.pdfReading.model.BookResponse;
import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import lombok.AllArgsConstructor;
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
  private final CategoryService categoryService;
  private final MultipartFileSaver fileSaver;
  private final PdfReadingAPI pdfReadingAPI;

  public List<Book> getAllByCriteria(
      String author, String title, String category, Integer page, Integer pageSize) {
    Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(DESC, "author"));
    return dao.findByCriteria(author, title, category, pageable);
  }

  public List<Book> getAllRecommendedBooksFor(String userId) {
    List<BookReaction> reactedBooks = reactionService.getAllBy(userId);
    return dataProcesser.process(
        reactedBooks, repository.findAll(), categoryReactionService.getAllBy(userId), userId);
  }

  public Book getById(String id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Book with id: " + id + " not found"));
  }

  public Book uploadNewBook(
      MultipartFile bookAsMultipartFile, MultipartFile pictureAsMultipartFile, String category) {
    String filename = bookAsMultipartFile.getOriginalFilename();
    String pictureName = pictureAsMultipartFile.getOriginalFilename();

    File savedMultipart = fileSaver.apply(bookAsMultipartFile);
    File savedPictureBook = fileSaver.apply(pictureAsMultipartFile);

    Category persistedCategory = categoryService.getByName(category);

    bucketComponent.upload(savedMultipart, bookAsMultipartFile.getName());
    bucketComponent.upload(savedPictureBook, pictureAsMultipartFile.getName());

    BookResponse processedBook = pdfReadingAPI.apply(savedMultipart);
    return repository.save(
        Book.builder()
            .category(persistedCategory)
            .filename(filename)
            .title(processedBook.getTitle())
            .author(processedBook.getAuthor())
            .pictureName(pictureName)
            .build());
  }

  public URL getPresignedUrlFromFilename(String filename) {
    return bucketComponent.presign(filename, Duration.ofMinutes(10));
  }
}
