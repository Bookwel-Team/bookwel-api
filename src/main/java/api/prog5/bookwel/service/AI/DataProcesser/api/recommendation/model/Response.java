package api.prog5.bookwel.service.AI.DataProcesser.api.recommendation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Generated
public class Response {
  @JsonProperty("recommendations")
  private List<Book> recommendations;
}
