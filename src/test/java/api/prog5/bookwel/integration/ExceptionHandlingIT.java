package api.prog5.bookwel.integration;

import static api.prog5.bookwel.utils.TestUtils.assertThrowsApiException;
import static org.mockito.Mockito.when;

import api.prog5.bookwel.endpoint.rest.api.HealthControllerApi;
import api.prog5.bookwel.endpoint.rest.client.ApiClient;
import api.prog5.bookwel.endpoint.rest.controller.HealthController;
import api.prog5.bookwel.integration.mocks.CustomFacadeIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

public class ExceptionHandlingIT extends CustomFacadeIT {
  @LocalServerPort private int serverPort;
  @MockBean HealthController healthController;

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
}
