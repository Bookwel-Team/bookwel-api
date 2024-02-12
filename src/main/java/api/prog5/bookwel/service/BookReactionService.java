package api.prog5.bookwel.service;

import static api.prog5.bookwel.endpoint.rest.model.ReactionStatus.UNSET;

import api.prog5.bookwel.endpoint.rest.model.ReactionStatus;
import api.prog5.bookwel.repository.BookReactionRepository;
import api.prog5.bookwel.repository.model.BookReaction;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookReactionService {

  private final BookReactionRepository repository;

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

  public List<BookReaction> getAllBy(String reactorId) {
    return repository.findAllByReactorId(reactorId);
  }

  public int countAllBy(String bookId, ReactionStatus status){
      return repository.countAllByBookIdAndReaction(bookId,status);
  }

  public ReactionStatus getReactionStatusBy(String bookId, String reactorId){
      return repository.findByBookIdAndReactorId(bookId, reactorId).map(BookReaction::getReaction).orElse(UNSET);
  }
}
