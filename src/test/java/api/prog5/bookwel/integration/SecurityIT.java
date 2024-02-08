package api.prog5.bookwel.integration;

import static api.prog5.bookwel.integration.mocks.MockData.USER_ONE_ID_TOKEN;
import static api.prog5.bookwel.integration.mocks.MockData.userOne;
import static api.prog5.bookwel.utils.TestUtils.assertThrowsForbiddenException;
import static org.junit.jupiter.api.Assertions.assertEquals;

import api.prog5.bookwel.endpoint.rest.api.SecurityApi;
import api.prog5.bookwel.endpoint.rest.api.UsersApi;
import api.prog5.bookwel.endpoint.rest.client.ApiClient;
import api.prog5.bookwel.endpoint.rest.client.ApiException;
import api.prog5.bookwel.endpoint.rest.model.Whoami;
import api.prog5.bookwel.integration.mocks.CustomFacadeIT;
import api.prog5.bookwel.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class SecurityIT extends CustomFacadeIT {
  @LocalServerPort private int serverPort;

  @Test
  void unknown_client_try_to_access_application_ko() {
    ApiClient unknownClient = new ApiClient();
    unknownClient.setScheme("http");
    unknownClient.setHost("localhost");
    unknownClient.setPort(serverPort);
    UsersApi api = new UsersApi(unknownClient);

    assertThrowsForbiddenException(api::getAllUsers, "Bad credentials");
  }

  private ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, serverPort);
  }

  @Test
  void whoami_ok() throws ApiException {
    ApiClient apiClient = anApiClient(USER_ONE_ID_TOKEN);
    SecurityApi securityApi = new SecurityApi(apiClient);

    Whoami actual = securityApi.whoami();

    assertEquals(userOne(), actual.getUser());
  }
}
