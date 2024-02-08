package api.prog5.bookwel.integration;

import static api.prog5.bookwel.integration.mocks.MockData.NON_EXISTENT_BOOK_ID;
import static api.prog5.bookwel.integration.mocks.MockData.USER_ONE_ID_TOKEN;
import static api.prog5.bookwel.integration.mocks.MockData.bookOne;
import static api.prog5.bookwel.integration.mocks.MockData.bookTwo;
import static api.prog5.bookwel.integration.mocks.MockData.createdBook;
import static api.prog5.bookwel.utils.TestUtils.assertThrowsApiException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static software.amazon.awssdk.core.internal.util.ChunkContentUtils.CRLF;

import api.prog5.bookwel.endpoint.rest.api.BookApi;
import api.prog5.bookwel.endpoint.rest.client.ApiClient;
import api.prog5.bookwel.endpoint.rest.client.ApiException;
import api.prog5.bookwel.endpoint.rest.model.Book;
import api.prog5.bookwel.integration.mocks.CustomFacadeIT;
import api.prog5.bookwel.service.BookService;
import api.prog5.bookwel.utils.TestUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.primitives.Bytes;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@Slf4j
public class BookIT extends CustomFacadeIT {
  @LocalServerPort private int serverPort;
  @Autowired BookService bookService;
  ObjectMapper om = new ObjectMapper();
  String filepath = "Securite.pdf";
  File file = getMock();

  File getMock() {
    URL resource = this.getClass().getClassLoader().getResource(filepath);
    return new File(resource.getFile());
  }

  private ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, serverPort);
  }

  @Test
  void get_books_ok() throws ApiException {
    ApiClient userOneClient = anApiClient(USER_ONE_ID_TOKEN);
    BookApi api = new BookApi(userOneClient);

    List<Book> books = api.getBooks(null, null, null, 1, 30).stream().map(this::ignoreFilelink).toList();
    List<Book> authorFilteredBooks =
        api.getBooks("one", null,null, null, null).stream().map(this::ignoreFilelink).toList();
    List<Book> categoryFilteredBooks =
        api.getBooks(null, null,"Bio", null, null).stream().map(this::ignoreFilelink).toList();
    List<Book> titleFilteredBooks =
        api.getBooks(null, "first","Bio", null, null).stream().map(this::ignoreFilelink).toList();
    List<Book> fullFilteredBooks =
        api.getBooks("one", null,"Bio", null, null).stream().map(this::ignoreFilelink).toList();
    List<Book> recommendedBooksOnly =
            api.getRecommendedBooks().stream().map(this::ignoreFilelink).toList();

    assertTrue(books.containsAll(List.of(bookOne(), bookTwo())));
    assertTrue(authorFilteredBooks.contains(bookOne()));
    assertFalse(authorFilteredBooks.contains(bookTwo()));
    assertTrue(categoryFilteredBooks.containsAll(List.of(bookOne(), bookTwo())));
    assertTrue(titleFilteredBooks.contains(bookOne()));
    assertTrue(fullFilteredBooks.contains(bookOne()));
    assertFalse(fullFilteredBooks.contains(bookTwo()));
    assertTrue(recommendedBooksOnly.contains(bookOne()));
  }

  @Test
  void get_book_ok() throws ApiException {
    ApiClient userOneClient = anApiClient(USER_ONE_ID_TOKEN);
    BookApi api = new BookApi(userOneClient);
    var expected = bookOne();

    Book actual = api.getBookById(expected.getId());

    actual = ignoreFilelink(actual);
    assertEquals(expected, actual);
  }

  @Test
  void get_not_existing_book_ko() {
    ApiClient userOneClient = anApiClient(USER_ONE_ID_TOKEN);
    BookApi api = new BookApi(userOneClient);

    assertThrowsApiException(
        "{"
            + "\"type\":\"404 NOT_FOUND\","
            + "\"message\":\"Book with id: "
            + NON_EXISTENT_BOOK_ID
            + " not found\""
            + "}",
        () -> api.getBookById(NON_EXISTENT_BOOK_ID));
  }

  @Test
  @SneakyThrows
  void create_book_ok() {
    HttpClient client = HttpClient.newHttpClient();
    String basePath = "http://localhost:" + serverPort;

    String boundary = "---------------------------" + System.currentTimeMillis();

    String contentTypeHeader = "multipart/form-data; boundary=" + boundary;

    String requestBodyPrefix =
        "--"
            + boundary
            + CRLF
            + "Content-Disposition: form-data; name=\"book\"; filename=\""
            + file.getName()
            + "\""
            + CRLF
            + "Content-Type: application/octet-stream"
            + CRLF
            + CRLF;

    byte[] fileBytes = Files.readAllBytes(Paths.get(file.getPath()));

    String requestBodySuffix = CRLF + "--" + boundary + "--" + CRLF;

    byte[] requestBody =
        Bytes.concat(requestBodyPrefix.getBytes(), fileBytes, requestBodySuffix.getBytes());

    InputStream requestBodyStream = new ByteArrayInputStream(requestBody);
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(basePath + "/books"))
            .header("Content-Type", contentTypeHeader)
            .header("Authorization", "Bearer " + USER_ONE_ID_TOKEN)
            .PUT(HttpRequest.BodyPublishers.ofInputStream(() -> requestBodyStream))
            .build();

    HttpResponse<InputStream> response =
        client.send(request, HttpResponse.BodyHandlers.ofInputStream());

    Book book = om.readValue(response.body(), new TypeReference<Book>() {});
    ignoreId(book);
    assertEquals(200, response.statusCode());
    assertEquals(createdBook(), book);
  }

  private Book ignoreFilelink(Book book) {
    book.setFileLink(null);
    return book;
  }

  private Book ignoreId(Book book) {
    book.setId(null);
    return book;
  }
}
