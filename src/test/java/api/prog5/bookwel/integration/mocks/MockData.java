package api.prog5.bookwel.integration.mocks;

import static api.prog5.bookwel.model.User.Role.ADMIN;
import static api.prog5.bookwel.model.User.Role.CLIENT;
import static org.mockito.Mockito.when;

import api.prog5.bookwel.endpoint.rest.model.Book;
import api.prog5.bookwel.endpoint.rest.security.auth.firebase.FirebaseAuthenticator;
import api.prog5.bookwel.model.User;

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

  public static String USER_TWO_ID_TOKEN = "user_one_id_token";

  void setupFirebase(FirebaseAuthenticator authenticator) {
    when(authenticator.getEmail(USER_ONE_ID_TOKEN)).thenReturn(USER_ONE_EMAIL);
    when(authenticator.getEmail(USER_TWO_ID_TOKEN)).thenReturn(USER_TWO_EMAIL);
  }

  public static Book bookOne(){
    return Book.builder()
            .id(BOOK_ONE_ID)
            .author("Author one")
            .category("Biopic")
            .title("The first book")
            .build();
  }

  public static Book bookTwo(){
    return Book.builder()
            .id(BOOK_TWO_ID)
            .author("Author two")
            .category("Romance")
            .title("The second book")
            .build();
  }
}
