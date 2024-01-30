package api.prog5.bookwel.repository.model;

import static jakarta.persistence.EnumType.STRING;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnTransformer;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name = "user_table")
public class User implements Serializable {

  @Id private String id;

  private String firstName;
  private String lastName;
  private String firebaseId;

  @Column(unique = true)
  private String email;

  @Enumerated(STRING)
  @Column(name = "status")
  @ColumnTransformer(write = "?::user_status")
  private Role role;

  public enum Role {
    ADMIN,
    CLIENT
  }
}
