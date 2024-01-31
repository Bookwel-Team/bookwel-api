package api.prog5.bookwel.service;

import api.prog5.bookwel.endpoint.rest.exception.NotFoundException;
import api.prog5.bookwel.endpoint.rest.model.CrupdateReaction;
import api.prog5.bookwel.endpoint.rest.model.ReactionStatus;
import api.prog5.bookwel.model.Book;
import api.prog5.bookwel.model.BookReaction;
import api.prog5.bookwel.model.User;
import api.prog5.bookwel.repository.BookReactionRepository;
import api.prog5.bookwel.repository.BookRepository;
import api.prog5.bookwel.repository.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookReactionService {

  private final BookReactionRepository bookReactionRepository;
  private final BookRepository bookRepository;
  private final UserRepository userRepository;

  public List<BookReaction> getReactionByBook(String bookId, ReactionStatus status) {
    Book book =
        bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Book not found"));
    return status == null
        ? bookReactionRepository.findAllByBook(book)
        : bookReactionRepository.findAllByBookAndReaction(book, status);
  }

  public BookReaction crupdateBookReaction(CrupdateReaction rest, String bookId) {
    Book book =
        bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Book not found"));
    User user =
        userRepository
            .findById(rest.getReactorId())
            .orElseThrow(() -> new NotFoundException("User not found"));
    return bookReactionRepository.save(
        BookReaction.builder()
            .id(rest.getId())
            .book(book)
            .reaction(rest.getReactionStatus())
            .reactor(user)
            .build());
  }
}
