package api.prog5.bookwel.service.AI.DataProcesser.api.recommendation;

import static api.prog5.bookwel.endpoint.rest.exception.ApiException.ExceptionType.SERVER_EXCEPTION;

import api.prog5.bookwel.Generated;
import api.prog5.bookwel.endpoint.rest.exception.ApiException;
import api.prog5.bookwel.service.AI.DataProcesser.api.recommendation.model.Book;
import api.prog5.bookwel.service.AI.DataProcesser.api.recommendation.model.Category;
import api.prog5.bookwel.service.AI.DataProcesser.api.recommendation.model.Payload;
import api.prog5.bookwel.service.AI.DataProcesser.api.recommendation.model.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Generated
public class RecommendationAPI {
  private final URI baseUrl;
  private final ObjectMapper om;

  public RecommendationAPI(@Value("${book.recommendation.api.url}") String baseUrl, ObjectMapper om)
      throws URISyntaxException {
    this.baseUrl = new URI(baseUrl);
    this.om = om;
  }

  public List<Book> apply(
      List<Book> filteredBooks,
      List<api.prog5.bookwel.repository.model.Book> notFilteredBooks,
      List<Category> categories) {
    RestTemplate restTemplate = new RestTemplate();
    UriComponentsBuilder uriComponentsBuilder =
        UriComponentsBuilder.fromUri(baseUrl).path("/recommandation/recommandation/");

    HttpEntity<String> request;
    try {
      request =
          new HttpEntity<>(
              om.writeValueAsString(
                  Payload.builder()
                      .allBooks(notFilteredBooks)
                      .userBooks(filteredBooks)
                      .userCategories(categories)
                      .build()));
    } catch (JsonProcessingException e) {
      throw new ApiException(SERVER_EXCEPTION, e.getMessage());
    }

    ResponseEntity<Response> responseEntity =
        restTemplate.postForEntity(uriComponentsBuilder.toUriString(), request, Response.class);

    return responseEntity.getBody().getRecommendations();
  }
}
