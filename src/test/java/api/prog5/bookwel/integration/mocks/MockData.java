package api.prog5.bookwel.integration.mocks;

import static api.prog5.bookwel.model.User.Role.ADMIN;
import static api.prog5.bookwel.model.User.Role.CLIENT;
import static org.mockito.Mockito.when;

import api.prog5.bookwel.endpoint.rest.security.auth.firebase.FirebaseAuthenticator;
import api.prog5.bookwel.model.User;

public class MockData {
  public static String USER_ONE_ID = "user_one";
  public static String USER_ONE_EMAIL = "first@gmail.com";
  public static String USER_TWO_ID = "user_two";
  public static String USER_TWO_EMAIL = "second@gmail.com";

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
}
