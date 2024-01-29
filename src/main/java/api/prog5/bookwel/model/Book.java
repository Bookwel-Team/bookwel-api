package api.prog5.bookwel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private String id;

    private String author;
    private String title;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String fileLink;

}
