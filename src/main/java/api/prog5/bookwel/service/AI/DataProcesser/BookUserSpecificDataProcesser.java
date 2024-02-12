package api.prog5.bookwel.service.AI.DataProcesser;

import api.prog5.bookwel.Generated;
import api.prog5.bookwel.repository.model.Book;
import api.prog5.bookwel.repository.model.BookReaction;
import api.prog5.bookwel.repository.model.CategoryReaction;
import api.prog5.bookwel.service.AI.DataProcesser.api.recommendation.RecommendationAPI;
import api.prog5.bookwel.service.AI.DataProcesser.api.recommendation.mapper.BookMapper;
import api.prog5.bookwel.service.AI.DataProcesser.api.recommendation.mapper.CategoryMapper;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Generated
public class BookUserSpecificDataProcesser
    implements TriUserSpecificDataProcesser<
        List<BookReaction>, List<Book>, List<CategoryReaction>, List<Book>> {
  private final RecommendationAPI recommendationAPI;
  private final BookMapper mapper;
  private final CategoryMapper categoryMapper;

  @Override
  public List<Book> process(
      List<BookReaction> reactedBooks,
      List<Book> allBooks,
      List<CategoryReaction> categoryReactions,
      String userId) {
    return recommendationAPI
        .apply(
            reactedBooks.stream().map(book -> mapper.toApiModel(book, userId)).toList(),
            allBooks,
            categoryReactions.stream()
                .map(category -> categoryMapper.toApiModel(category, userId))
                .toList())
        .stream()
        .map(mapper::toDomain)
        .toList();
  }
}
