package api.prog5.bookwel.repository;

import api.prog5.bookwel.endpoint.rest.model.ReactionStatus;
import api.prog5.bookwel.repository.model.Book;
import api.prog5.bookwel.repository.model.BookReaction;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReactionRepository extends JpaRepository<BookReaction, String> {
  List<BookReaction> findAllByBookId(String bookId);

  List<BookReaction> findAllByBookIdAndReaction(String bookId, ReactionStatus reactionStatus);

  Optional<BookReaction> findByBookIdAndReactorId(String bookId, String reactorId);

  List<BookReaction> findAllByReactorId(String reactorId);
}
