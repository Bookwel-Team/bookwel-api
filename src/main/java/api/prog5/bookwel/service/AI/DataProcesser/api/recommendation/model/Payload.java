package api.prog5.bookwel.service.AI.DataProcesser.api.recommendation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Generated
public class Payload implements Serializable {
  @JsonProperty("books")
  private List<Book> userBooks;

  @JsonProperty("all_books")
  private List<api.prog5.bookwel.repository.model.Book> allBooks;
  @JsonProperty("user_categories")
  private List<Category> userCategories;
}
