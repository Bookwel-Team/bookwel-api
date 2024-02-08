package api.prog5.bookwel.integration;

import static api.prog5.bookwel.endpoint.rest.model.ReactionStatus.LIKE;
import static api.prog5.bookwel.integration.mocks.MockData.USER_TWO_ID_TOKEN;
import static api.prog5.bookwel.integration.mocks.MockData.bookOne;
import static api.prog5.bookwel.integration.mocks.MockData.crupdateReaction;
import static org.junit.jupiter.api.Assertions.assertEquals;

import api.prog5.bookwel.endpoint.rest.api.ReactionApi;
import api.prog5.bookwel.endpoint.rest.client.ApiClient;
import api.prog5.bookwel.endpoint.rest.client.ApiException;
import api.prog5.bookwel.endpoint.rest.model.BookReaction;
import api.prog5.bookwel.integration.mocks.CustomFacadeIT;
import api.prog5.bookwel.utils.TestUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@Slf4j
public class ReactionIT extends CustomFacadeIT {
  @LocalServerPort private int serverPort;

  private ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, serverPort);
  }

  @Test
  void crupdate_reaction() throws ApiException {
    ApiClient userTwoClient = anApiClient(USER_TWO_ID_TOKEN);
    ReactionApi api = new ReactionApi(userTwoClient);

    BookReaction book = api.crupdateReactionToABook(bookOne().getId(), crupdateReaction());

    assertEquals(book.getReactionStatus().getValue(), LIKE.toString());
  }
}
