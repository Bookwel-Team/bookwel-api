package api.prog5.bookwel.endpoint.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    private String id;
    private String author;
    private String category;
    private String fileLink;
    private String title;

}
