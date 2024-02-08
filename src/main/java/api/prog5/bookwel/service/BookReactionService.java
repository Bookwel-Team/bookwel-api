package api.prog5.bookwel.service;

import api.prog5.bookwel.endpoint.rest.model.ReactionStatus;
import api.prog5.bookwel.repository.BookReactionRepository;
import api.prog5.bookwel.repository.model.Book;
import api.prog5.bookwel.repository.model.BookReaction;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookReactionService {

  private final BookReactionRepository repository;
  private final BookService service;

  public List<BookReaction> getReactionsByBook(String bookId, ReactionStatus status) {
    Book book = service.getById(bookId);
    return status == null
        ? repository.findAllByBook(book)
        : repository.findAllByBookAndReaction(book, status);
  }

  public BookReaction crupdateBookReaction(BookReaction toSave) {
    Optional<BookReaction> optionalExistingReaction =
        repository.findByBookIdAndReactorId(toSave.getBook().getId(), toSave.getReactor().getId());
    return repository.save(
        optionalExistingReaction
            .map(
                br -> {
                  br.setReaction(toSave.getReaction());
                  return br;
                })
            .orElse(toSave));
  }
}
