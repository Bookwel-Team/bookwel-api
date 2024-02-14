package api.prog5.bookwel.service.AI.DataProcesser.api.pdfReading;

import static api.prog5.bookwel.endpoint.rest.exception.ApiException.ExceptionType.SERVER_EXCEPTION;

import api.prog5.bookwel.Generated;
import api.prog5.bookwel.endpoint.rest.exception.ApiException;
import api.prog5.bookwel.service.AI.DataProcesser.api.pdfReading.model.BookResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Generated
public class PdfReadingAPI {
  private final URI baseUrl;

  public PdfReadingAPI(@Value("${pdf.reading.api.url}") String baseUrl) throws URISyntaxException {
    this.baseUrl = new URI(baseUrl);
  }

  public BookResponse apply(File file) {
    UriComponentsBuilder uriComponentsBuilder =
        UriComponentsBuilder.fromUri(baseUrl).path("/recommandation/pdf/");
    try {
      var response =
          Unirest.post(uriComponentsBuilder.toUriString())
              .field("pdf-content", file)
              .asObject(BookResponse.class);
      return response.getBody();
    } catch (UnirestException e) {
      throw new ApiException(SERVER_EXCEPTION, e.getMessage());
    }
  }
}
