package api.prog5.bookwel.service.AI.DataProcesser.api.pdfReading.model;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponse implements Serializable {
  private String title;
  private String author;
}
