package api.prog5.bookwel.integration.mocks;

import static api.prog5.bookwel.endpoint.rest.model.UserStatus.ADMIN;
import static api.prog5.bookwel.endpoint.rest.model.UserStatus.CLIENT;

import api.prog5.bookwel.endpoint.rest.model.Book;
import api.prog5.bookwel.endpoint.rest.model.Category;
import api.prog5.bookwel.endpoint.rest.model.User;
import api.prog5.bookwel.endpoint.rest.model.UserProfile;

public class MockData {
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

  public static UserProfile userProfile(){
    return new UserProfile()
            .firstName("John")
            .lastName("Doe")
            .email("john.doe@gmail.com");
  }

  public static User expectedAdminAfterUpdate(){
    return new User()
            .id(USER_ONE_ID)
            .profile(userProfile())
            .firebaseId(null)
            .status(ADMIN);
  }

  public static User expectedClientAfterUpdate(){
    return new User()
            .id(USER_TWO_ID)
            .profile(userProfile())
            .firebaseId(null)
            .status(CLIENT);
  }
  public static String USER_ONE_ID_TOKEN = "user_one_id_token";

  public static String USER_TWO_ID_TOKEN = "user_two_id_token";

  public static Book bookOne() {
    return new Book()
        .id(BOOK_ONE_ID)
        .author("Author one")
        .category("Biopic")
        .title("The first book")
        .fileLink(null)
        .fileName("none");
  }

  public static Book bookTwo() {
    return new Book()
        .id(BOOK_TWO_ID)
        .author("Author two")
        .category("Biopic")
        .title("The second book")
        .fileLink(null)
        .fileName("none");
  }

  public static Category categoryOne() {
    return new Category().id("category_one_id").name("Biopic");
  }

  public static Category categoryTwo() {
    return new Category().id("category_two_id").name("Romance");
  }
}
