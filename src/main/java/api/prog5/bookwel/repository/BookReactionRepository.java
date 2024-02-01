package api.prog5.bookwel.repository;

import api.prog5.bookwel.endpoint.rest.model.ReactionStatus;
import api.prog5.bookwel.model.Book;
import api.prog5.bookwel.model.BookReaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReactionRepository extends JpaRepository<BookReaction, String> {
  List<BookReaction> findAllByBook(Book book);

  List<BookReaction> findAllByBookAndReaction(Book book, ReactionStatus reactionStatus);
}