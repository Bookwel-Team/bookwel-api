package api.prog5.bookwel.integration.mocks;

import static api.prog5.bookwel.repository.model.User.Role.ADMIN;
import static api.prog5.bookwel.repository.model.User.Role.CLIENT;

import api.prog5.bookwel.endpoint.rest.model.Book;
import api.prog5.bookwel.repository.model.User;

public class MockData {
  public static String USER_ONE_ID = "user_one";
  public static String USER_ONE_EMAIL = "user.one@gmail.com";
  public static String USER_TWO_ID = "user_two";
  public static String USER_TWO_EMAIL = "user.two@gmail.com";

  public static String BOOK_ONE_ID = "book_one_id";
  public static String BOOK_TWO_ID = "book_two_id";

  public static User userOne() {
    return User.builder()
        .id(USER_ONE_ID)
        .firstName("One")
        .lastName("First")
        .email(USER_ONE_EMAIL)
        .role(ADMIN)
        .build();
  }

  public static User userTwo() {
    return User.builder()
        .id(USER_TWO_ID)
        .firstName("One")
        .lastName("First")
        .email(USER_TWO_EMAIL)
        .role(CLIENT)
        .build();
  }

  public static String USER_ONE_ID_TOKEN = "user_one_id_token";

  public static String USER_TWO_ID_TOKEN = "user_two_id_token";

  public static Book bookOne() {
    return new Book()
        .id(BOOK_ONE_ID)
        .author("Author one")
        .category("Biopic")
        .title("The first book");
  }

  public static Book bookTwo() {
    return new Book()
        .id(BOOK_TWO_ID)
        .author("Author two")
        .category("Romance")
        .title("The second book");
  }
}
