package api.prog5.bookwel.repository.model;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

import api.prog5.bookwel.endpoint.rest.model.ReactionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name = "category_reaction")
public class CategoryReaction {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private String id;

  @Enumerated(STRING)
  @Column(name = "reaction")
  @ColumnTransformer(write = "?::reaction_status")
  private ReactionStatus reaction;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User reactor;

  @CreationTimestamp private Instant creationDatetime;
}
