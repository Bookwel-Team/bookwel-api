package api.prog5.bookwel.integration;


import api.prog5.bookwel.endpoint.rest.api.AiApi;
import api.prog5.bookwel.endpoint.rest.client.ApiClient;
import api.prog5.bookwel.endpoint.rest.client.ApiException;
import api.prog5.bookwel.endpoint.rest.model.AiResponse;
import api.prog5.bookwel.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static api.prog5.bookwel.integration.mocks.MockData.USER_ONE_ID_TOKEN;
@Testcontainers
public class ChatIT {
  @LocalServerPort
  private int serverPort;
  private ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, serverPort);
  }

  @Test
  public void chat_bot_Ok() throws ApiException {
    ApiClient userOneClient = anApiClient(USER_ONE_ID_TOKEN);
    AiApi api = new AiApi(userOneClient);

    String userPrompt = "Book like Harry Potter";
    List<AiResponse> response = api.chat(userPrompt);

    Assertions.assertNotNull(response);
    Assertions.assertFalse(response.isEmpty());
  }
}
