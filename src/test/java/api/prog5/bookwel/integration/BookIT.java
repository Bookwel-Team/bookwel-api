package api.prog5.bookwel.integration;

import static api.prog5.bookwel.integration.mocks.MockData.MOCK_FILE_NAME;
import static api.prog5.bookwel.integration.mocks.MockData.MOCK_PICTURE_NAME;
import static api.prog5.bookwel.integration.mocks.MockData.NON_EXISTENT_BOOK_ID;
import static api.prog5.bookwel.integration.mocks.MockData.USER_ONE_ID;
import static api.prog5.bookwel.integration.mocks.MockData.USER_ONE_ID_TOKEN;
import static api.prog5.bookwel.integration.mocks.MockData.bookOne;
import static api.prog5.bookwel.integration.mocks.MockData.bookTwo;
import static api.prog5.bookwel.integration.mocks.MockData.createdBook;
import static api.prog5.bookwel.integration.mocks.MockData.dummyBookResponse;
import static api.prog5.bookwel.utils.TestUtils.assertThrowsApiException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import api.prog5.bookwel.endpoint.rest.api.BookApi;
import api.prog5.bookwel.endpoint.rest.client.ApiClient;
import api.prog5.bookwel.endpoint.rest.client.ApiException;
import api.prog5.bookwel.endpoint.rest.model.Book;
import api.prog5.bookwel.endpoint.rest.model.ReactionStatistics;
import api.prog5.bookwel.integration.mocks.CustomFacadeIT;
import api.prog5.bookwel.service.AI.DataProcesser.api.pdfReading.model.BookResponse;
import api.prog5.bookwel.service.BookService;
import api.prog5.bookwel.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@AutoConfigureMockMvc
public class BookIT extends CustomFacadeIT {
  @LocalServerPort private int serverPort;
  @Autowired BookService bookService;
  @Autowired ObjectMapper om;
  @Autowired MockMvc mockMvc;

  File getFileFromResource(String resourceName) {
    URL resource = this.getClass().getClassLoader().getResource(resourceName);
    return new File(resource.getFile());
  }

  private ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, serverPort);
  }

  @Test
  void get_books_ok() throws ApiException {
    ApiClient userOneClient = anApiClient(USER_ONE_ID_TOKEN);
    BookApi api = new BookApi(userOneClient);

    List<Book> books =
        api.getBooks(null, null, null, USER_ONE_ID, 1, 30).stream()
            .map(this::unsetFileLinkAndReactionStatistics)
            .map(this::unsetPictureLink)
            .toList();
    List<Book> authorFilteredBooks =
        api.getBooks("one", null, null, null, null, null).stream()
            .map(this::unsetFileLinkAndReactionStatistics)
            .map(this::unsetPictureLink)
            .toList();
    List<Book> categoryFilteredBooks =
        api.getBooks(null, null, "Bio", null, null, null).stream()
            .map(this::unsetFileLinkAndReactionStatistics)
            .map(this::unsetPictureLink)
            .toList();
    List<Book> titleFilteredBooks =
        api.getBooks(null, "first", "Bio", null, null, null).stream()
            .map(this::unsetFileLinkAndReactionStatistics)
            .map(this::unsetPictureLink)
            .toList();
    List<Book> fullFilteredBooks =
        api.getBooks("one", null, "Bio", null, null, null).stream()
            .map(this::unsetFileLinkAndReactionStatistics)
            .map(this::unsetPictureLink)
            .toList();
    List<Book> recommendedBooksOnly =
        api.getRecommendedBooks().stream()
            .map(this::unsetFileLinkAndReactionStatistics)
            .map(this::unsetPictureLink)
            .toList();

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

    actual = unsetFileLinkAndReactionStatistics(actual);
    actual = unsetPictureLink(actual);
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
    BookResponse dummyBookResponse = dummyBookResponse();
    Book expected =
        createdBook("Science", dummyBookResponse.getAuthor(), dummyBookResponse.getTitle());
    String picture = om.writeValueAsString(getFileFromResource(MOCK_PICTURE_NAME));
    String pdf = om.writeValueAsString(getFileFromResource(MOCK_FILE_NAME));

    MockMultipartFile picMock =
        new MockMultipartFile(
            "picture", MOCK_PICTURE_NAME, "image/jpeg", picture.getBytes(StandardCharsets.UTF_8));
    MockMultipartFile pdfMock =
        new MockMultipartFile(
            "book", MOCK_FILE_NAME, "application/json", pdf.getBytes(StandardCharsets.UTF_8));
    MockMultipartFile catMock =
        new MockMultipartFile(
            "category", "", "text/plain", "Science".getBytes(StandardCharsets.UTF_8));

    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.multipart(HttpMethod.POST, "/books")
                    .file(picMock)
                    .file(pdfMock)
                    .file(catMock)
                    .header("Authorization", "Bearer " + USER_ONE_ID_TOKEN))
            .andExpect(status().isOk())
            .andReturn();
    Book book = om.readValue(result.getResponse().getContentAsString(), Book.class);
    book = ignoreId(book);
    book = unsetFileLinkAndReactionStatistics(book);
    book = unsetPictureLink(book);
    assertEquals(expected, book);
  }

  private Book unsetFileLinkAndReactionStatistics(Book book) {
    book.setFileLink(null);
    book.setReactionStatistics(new ReactionStatistics());
    return book;
  }

  private Book unsetPictureLink(Book book) {
    book.setPictureLink(null);
    return book;
  }

  private Book ignoreId(Book book) {
    book.setId(null);
    return book;
  }
}
