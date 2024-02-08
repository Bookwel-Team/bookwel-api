package api.prog5.bookwel.integration;

import static api.prog5.bookwel.integration.mocks.MockData.NON_EXISTENT_BOOK_ID;
import static api.prog5.bookwel.integration.mocks.MockData.USER_ONE_ID_TOKEN;
import static api.prog5.bookwel.integration.mocks.MockData.bookOne;
import static api.prog5.bookwel.integration.mocks.MockData.bookTwo;
import static api.prog5.bookwel.utils.TestUtils.assertThrowsApiException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import api.prog5.bookwel.endpoint.rest.api.BookApi;
import api.prog5.bookwel.endpoint.rest.client.ApiClient;
import api.prog5.bookwel.endpoint.rest.client.ApiException;
import api.prog5.bookwel.endpoint.rest.model.Book;
import api.prog5.bookwel.integration.mocks.CustomFacadeIT;
import api.prog5.bookwel.utils.TestUtils;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class BookIT extends CustomFacadeIT {
  @LocalServerPort private int serverPort;

  private ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, serverPort);
  }

  @Test
  void get_books_ok() throws ApiException {
    ApiClient userOneClient = anApiClient(USER_ONE_ID_TOKEN);
    BookApi api = new BookApi(userOneClient);

    List<Book> books = api.getBooks(null, null, null, null);
    books = books.stream().map(this::ignoreFilelink).collect(Collectors.toList());

    assertTrue(books.containsAll(List.of(bookOne(), bookTwo())));
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

  private Book ignoreFilelink(Book book) {
    book.setFileLink(null);
    return book;
  }
}
