package api.prog5.bookwel.integration.mocks;

import api.prog5.bookwel.endpoint.rest.model.*;
import api.prog5.bookwel.service.AI.DataProcesser.api.pdfReading.model.BookResponse;

import java.time.Instant;

import static api.prog5.bookwel.endpoint.rest.model.ReactionStatus.DISLIKE;
import static api.prog5.bookwel.endpoint.rest.model.ReactionStatus.LIKE;
import static api.prog5.bookwel.endpoint.rest.model.UserStatus.ADMIN;
import static api.prog5.bookwel.endpoint.rest.model.UserStatus.CLIENT;

public class MockData {
  public static final String MOCK_FILE_NAME = "Securite.pdf";
  public static final String MOCK_PICTURE_NAME = "Image.jpg";
  public static String BOOK_REACTION_ONE_ID = "reaction1_id";
  public static final String MOCK_PRESIGNED_URL = "http://localhost";
  public static String USER_ONE_ID = "user_one";
  public static String USER_ONE_EMAIL = "user.one@gmail.com";
  public static String USER_TWO_ID = "user_two";
  public static String USER_TWO_EMAIL = "user.two@gmail.com";

  public static String BOOK_ONE_ID = "book_one_id";
  public static String BOOK_TWO_ID = "book_two_id";
  public static String NON_EXISTENT_BOOK_ID = "non_existend_book_id";

  public static User userOne() {
    return new User()
        .id(USER_ONE_ID)
        .profile(new UserProfile().firstName("One").lastName("First").email(USER_ONE_EMAIL))
        .firebaseId(null)
        .status(ADMIN);
  }

  public static User userTwo() {
    return new User()
        .id(USER_TWO_ID)
        .profile(new UserProfile().firstName("Two").lastName("Second").email(USER_TWO_EMAIL))
        .firebaseId(null)
        .status(CLIENT);
  }

  public static UserProfile userProfile(String email) {
    return new UserProfile().firstName("John").lastName("Doe").email(email);
  }

  public static User expectedClientAfterUpdate(String email) {
    return new User().id(USER_TWO_ID).profile(userProfile(email)).firebaseId(null).status(CLIENT);
  }

  public static String USER_ONE_ID_TOKEN = "user_one_id_token";

  public static String USER_TWO_ID_TOKEN = "user_two_id_token";
  public static final String UNKNOWN_USER_TOKEN = "bad_token";

  public static Book bookOne() {
    return new Book()
        .id(BOOK_ONE_ID)
        .author("Author one")
        .category("Biopic")
        .title("The first book")
        .fileLink(null)
        .fileName("none")
        .pictureLink(null)
        .reactionStatistics(new ReactionStatistics());
  }

  public static Book bookTwo() {
    return new Book()
        .id(BOOK_TWO_ID)
        .author("Author two")
        .category("Biopic")
        .title("The second book")
        .fileLink(null)
        .fileName("none")
        .pictureLink(null)
        .reactionStatistics(new ReactionStatistics());
  }

  public static CrupdateReaction crupdateReaction() {
    return new CrupdateReaction().reactionStatus(LIKE).reactorId(USER_ONE_ID);
  }

  public static CategoryCrupdateReaction categoryCrupdateReaction() {
    return new CategoryCrupdateReaction()
        .reactionStatus(LIKE)
        .reactorId(USER_ONE_ID)
        .categoryId(categoryOne().getId());
  }

  public static CategoryReaction expectedCategoryReaction() {
    return new CategoryReaction()
        .id(null)
        .creationDatetime(null)
        .category("Biopic")
        .reactorName("First")
        .reactorId("user_one")
        .reactionStatus(LIKE);
  }

  public static BookReaction bookReaction1() {
    return new BookReaction()
        .id("book_reaction_1_id")
        .creationDatetime(Instant.parse("2024-02-08T11:18:38.662017Z"))
        .bookTitle("The first book")
        .reactorName("First")
        .reactorId("user_one")
        .reactionStatus(LIKE);
  }

  public static BookReaction bookReaction3() {
    return new BookReaction()
        .id("book_reaction_3_id")
        .creationDatetime(Instant.parse("2024-02-08T11:13:38.662017Z"))
        .bookTitle("The first book")
        .reactorName("Second")
        .reactorId("user_two")
        .reactionStatus(DISLIKE);
  }

  public static CategoryReaction categoryReaction1() {
    return new CategoryReaction()
        .id("category_reaction_1_id")
        .creationDatetime(Instant.parse("2024-02-08T11:15:38.662017Z"))
        .category("Biopic")
        .reactorName("First")
        .reactorId("user_one")
        .reactionStatus(LIKE);
  }

  public static CategoryReaction categoryReaction3() {
    return new CategoryReaction()
        .id("category_reaction_3_id")
        .creationDatetime(Instant.parse("2024-02-08T11:08:38.662017Z"))
        .category("Biopic")
        .reactorName("Second")
        .reactorId("user_two")
        .reactionStatus(LIKE);
  }

  public static Book createdBook(String category, String author, String title) {
    return new Book()
        .fileName(MOCK_FILE_NAME)
        .category(category)
        .author(author)
        .title(title)
        .reactionStatistics(new ReactionStatistics());
  }

  public static Category categoryOne() {
    return new Category()
        .id("category_one_id")
        .name("Biopic")
        .reactionStatistics(new ReactionStatistics());
  }

  public static Category categoryTwo() {
    return new Category()
        .id("category_two_id")
        .name("Romance")
        .reactionStatistics(new ReactionStatistics());
  }

  public static api.prog5.bookwel.service.AI.DataProcesser.api.recommendation.model.Book
      likedBookOneMockAsAiBook() {
    Book book = bookOne();
    var result = new api.prog5.bookwel.service.AI.DataProcesser.api.recommendation.model.Book();
    result.setId(book.getId());
    result.setCategory(new api.prog5.bookwel.repository.model.Category("id", book.getCategory()));
    result.setAuthor(book.getAuthor());
    result.setTitle(book.getTitle());
    result.setFilename(book.getFileName());
    result.setUserId(USER_ONE_ID);
    result.setUserReaction(LIKE);
    return result;
  }

  public static BookResponse dummyBookResponse() {
    return BookResponse.builder().author("dummy").title("dummy").build();
  }
  public static AiResponse dummyAiResponse() {
    return new AiResponse()
      .title("Dummy Title")
      .author("Dummy Author")
      .synopsis("Dummy Synopsis")
      .category("Dummy Category")
      .reason("Dummy Reason");
  }
}
