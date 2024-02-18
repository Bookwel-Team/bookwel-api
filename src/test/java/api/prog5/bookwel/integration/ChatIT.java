package api.prog5.bookwel.integration;


import api.prog5.bookwel.endpoint.rest.client.ApiClient;
import api.prog5.bookwel.endpoint.rest.client.ApiException;
import api.prog5.bookwel.endpoint.rest.model.AiResponse;
import api.prog5.bookwel.integration.mocks.AiApiMock;
import api.prog5.bookwel.integration.mocks.CustomFacadeIT;
import api.prog5.bookwel.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static api.prog5.bookwel.integration.mocks.MockData.USER_ONE_ID_TOKEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
public class ChatIT extends CustomFacadeIT {
  @LocalServerPort
  private int serverPort;
  private ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, serverPort);
  }

  @Test
  public void chat_bot_Ok() throws ApiException {
    ApiClient userOneClient = anApiClient(USER_ONE_ID_TOKEN);
    AiApiMock api = new AiApiMock(userOneClient);
    String prompt = "Dummy title";
    List<AiResponse> response = api.chat(prompt);


    assertNotNull(response);
    assertEquals(1, response.size());
  }
}
