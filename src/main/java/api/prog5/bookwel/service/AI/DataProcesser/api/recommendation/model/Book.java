package api.prog5.bookwel.service.AI.DataProcesser.api.recommendation.model;

import api.prog5.bookwel.Generated;
import api.prog5.bookwel.endpoint.rest.model.ReactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Generated
public class Book extends api.prog5.bookwel.repository.model.Book implements Reactable {
  private ReactionStatus userReaction;
  private String userId;
}
