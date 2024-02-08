package api.prog5.bookwel.integration;

import api.prog5.bookwel.endpoint.rest.api.UsersApi;
import api.prog5.bookwel.endpoint.rest.client.ApiClient;
import api.prog5.bookwel.integration.mocks.CustomFacadeIT;
import api.prog5.bookwel.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;

import static api.prog5.bookwel.utils.TestUtils.assertThrowsForbiddenException;

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

    assertThrowsForbiddenException(() -> api.getAllUsers(), "Bad credentials");
  }

}
