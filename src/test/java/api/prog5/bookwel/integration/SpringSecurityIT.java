package api.prog5.bookwel.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;

import api.prog5.bookwel.integration.mocks.CustomFacadeIT;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class SpringSecurityIT extends CustomFacadeIT {

  @LocalServerPort private int port;

  @Test
  void ping_with_cors() throws IOException, InterruptedException {
    HttpClient unauthenticatedClient = HttpClient.newBuilder().build();
    String basePath = "http://localhost:" + port;

    HttpResponse<String> response =
        unauthenticatedClient.send(
            HttpRequest.newBuilder()
                .uri(URI.create(basePath + "/hello"))
                // cors
                .header("Access-Control-Request-Method", "GET")
                .header("Origin", "http://localhost:3000")
                .build(),
            HttpResponse.BodyHandlers.ofString());
    assertEquals(HttpStatus.OK.value(), response.statusCode());
    assertEquals("Hello from the Bookwel Team!", response.body());
    // cors
    var headers = response.headers();
    var origins = headers.allValues("Access-Control-Allow-Origin");
    assertEquals(1, origins.size());
    assertEquals("*", origins.get(0));
  }

  @Test
  void options_has_cors_headers() throws IOException, InterruptedException {
    test_cors(GET, "/users");
  }

  void test_cors(HttpMethod method, String path) throws IOException, InterruptedException {
    HttpClient unauthenticatedClient = HttpClient.newBuilder().build();
    String basePath = "http://localhost:" + port;

    HttpResponse<String> response =
        unauthenticatedClient.send(
            HttpRequest.newBuilder()
                .uri(URI.create(basePath + path))
                .method(OPTIONS.name(), HttpRequest.BodyPublishers.noBody())
                .header("Access-Control-Request-Headers", "authorization")
                .header("Access-Control-Request-Method", method.name())
                .header("Origin", "http://localhost:3000")
                .build(),
            HttpResponse.BodyHandlers.ofString());

    var headers = response.headers();
    var origins = headers.allValues("Access-Control-Allow-Origin");
    assertEquals(1, origins.size());
    assertEquals("*", origins.get(0));
    var headersList = headers.allValues("Access-Control-Allow-Headers");
    assertEquals(1, headersList.size());
    assertEquals("authorization", headersList.get(0));
  }
}
