package api.prog5.bookwel.service;

import api.prog5.bookwel.endpoint.rest.model.ReactionStatus;
import api.prog5.bookwel.model.Book;
import api.prog5.bookwel.model.BookReaction;
import api.prog5.bookwel.repository.BookReactionRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookReactionService {

  private final BookReactionRepository bookReactionRepository;
  private final BookService bookService;

  public List<BookReaction> getReactionByBook(String bookId, ReactionStatus status) {
    Book book = bookService.getById(bookId);
    return status == null
        ? bookReactionRepository.findAllByBook(book)
        : bookReactionRepository.findAllByBookAndReaction(book, status);
  }

  public BookReaction crupdateBookReaction(BookReaction toSave) {
    return bookReactionRepository.save(toSave);
  }
}
