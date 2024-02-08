package api.prog5.bookwel.integration;

import static api.prog5.bookwel.integration.mocks.MockData.UNKNOWN_USER_TOKEN;
import static api.prog5.bookwel.integration.mocks.MockData.USER_TWO_ID_TOKEN;
import static api.prog5.bookwel.utils.TestUtils.assertThrowsApiException;
import static api.prog5.bookwel.utils.TestUtils.assertThrowsForbiddenException;
import static org.mockito.Mockito.when;

import api.prog5.bookwel.endpoint.rest.api.HealthControllerApi;
import api.prog5.bookwel.endpoint.rest.api.UsersApi;
import api.prog5.bookwel.endpoint.rest.client.ApiClient;
import api.prog5.bookwel.endpoint.rest.controller.HealthController;
import api.prog5.bookwel.integration.mocks.CustomFacadeIT;
import api.prog5.bookwel.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

public class ExceptionHandlingIT extends CustomFacadeIT {
  @LocalServerPort private int serverPort;
  @MockBean HealthController healthController;

  private ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, serverPort);
  }

  @Test
  void internal_server_error_is_mapped_to_rest() {
    when(healthController.checkHealth()).thenThrow(new RuntimeException());
    ApiClient unknownClient = new ApiClient();
    unknownClient.setScheme("http");
    unknownClient.setHost("localhost");
    unknownClient.setPort(serverPort);
    HealthControllerApi api = new HealthControllerApi(unknownClient);

    assertThrowsApiException(
        "{\"type\":\"500 INTERNAL_SERVER_ERROR\",\"message\":null}", api::checkHealth);
  }

  @Test
  void access_denied_is_correctly_handled() {
    ApiClient userTwoClient = anApiClient(USER_TWO_ID_TOKEN);
    UsersApi usersApi = new UsersApi(userTwoClient);

    // bookApi is a protected endpoint only accessible for authenticated clients;
    assertThrowsApiException(
        "{\"type\":\"403 FORBIDDEN\",\"message\":\"Access Denied\"}", usersApi::getAllUsers);
  }

  @Test
  void unknown_user_token_forbidden_ok() {
    ApiClient userTwoClient = anApiClient(UNKNOWN_USER_TOKEN);
    UsersApi usersApi = new UsersApi(userTwoClient);

    // bookApi is a protected endpoint only accessible for authenticated clients;
    assertThrowsForbiddenException(usersApi::getAllUsers, "Bad credentials");
  }
}
