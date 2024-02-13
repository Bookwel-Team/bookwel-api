package api.prog5.bookwel.integration;

import static api.prog5.bookwel.endpoint.rest.model.UserStatus.CLIENT;
import static api.prog5.bookwel.integration.mocks.MockData.USER_ONE_ID_TOKEN;
import static api.prog5.bookwel.integration.mocks.MockData.USER_TWO_EMAIL;
import static api.prog5.bookwel.integration.mocks.MockData.USER_TWO_ID_TOKEN;
import static api.prog5.bookwel.integration.mocks.MockData.expectedClientAfterUpdate;
import static api.prog5.bookwel.integration.mocks.MockData.userOne;
import static api.prog5.bookwel.integration.mocks.MockData.userProfile;
import static api.prog5.bookwel.integration.mocks.MockData.userTwo;
import static api.prog5.bookwel.utils.TestUtils.assertThrowsBadRequestException;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import api.prog5.bookwel.endpoint.rest.api.UsersApi;
import api.prog5.bookwel.endpoint.rest.client.ApiClient;
import api.prog5.bookwel.endpoint.rest.client.ApiException;
import api.prog5.bookwel.endpoint.rest.model.CreateUser;
import api.prog5.bookwel.endpoint.rest.model.User;
import api.prog5.bookwel.endpoint.rest.model.UserProfile;
import api.prog5.bookwel.endpoint.rest.model.UserStatus;
import api.prog5.bookwel.integration.mocks.CustomFacadeIT;
import api.prog5.bookwel.utils.TestUtils;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class UserIT extends CustomFacadeIT {
  @LocalServerPort private int serverPort;

  private ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, serverPort);
  }

  @Test
  void get_users_ok() throws ApiException {
    ApiClient userOneClient = anApiClient(USER_ONE_ID_TOKEN);
    UsersApi api = new UsersApi(userOneClient);

    List<User> users = api.getAllUsers();

    assertTrue(users.contains(userOne()));
  }

  @Test
  void get_user_ok() throws ApiException {
    ApiClient userOneClient = anApiClient(USER_ONE_ID_TOKEN);
    UsersApi api = new UsersApi(userOneClient);
    var expected = userOne();

    User actual = api.getUserById(expected.getId());

    assertEquals(expected, actual);
  }

  @Test
  void create_user_ok() throws ApiException {
    ApiClient userOneClient = anApiClient(USER_ONE_ID_TOKEN);
    UsersApi api = new UsersApi(userOneClient);
    CreateUser payload = creatableUser("mock@gmail.com");
    User expected = from(payload, CLIENT);

    User actual = api.createUser(payload);
    User updatedPayload = updateProfile(actual);
    User actualUpdated = api.updateUserProfile(actual.getId(), updatedPayload.getProfile());
    ignoreId(actual);
    ignoreId(actualUpdated);

    assertEquals(expected, actual);
    assertEquals(updatedPayload, actualUpdated);
  }

  @Test
  void create_user_ko() {
    ApiClient userOneClient = anApiClient(USER_ONE_ID_TOKEN);
    UsersApi api = new UsersApi(userOneClient);
    CreateUser payload = creatableUser(null);
    assertThrowsBadRequestException(() -> api.createUser(payload), "profile.email is mandatory.");
    assertThrowsBadRequestException(
        () -> api.createUser(payload.profile(null)), "profile is mandatory.");
  }

  @Test
  void update_user_profile_as_client() throws ApiException {
    ApiClient userOneClient = anApiClient(USER_TWO_ID_TOKEN);
    UsersApi api = new UsersApi(userOneClient);
    User user = userTwo();

    User actual = api.updateUserProfile(user.getId(), userProfile(USER_TWO_EMAIL));
    User expected = expectedClientAfterUpdate(USER_TWO_EMAIL);

    assertEquals(expected, actual);
  }

  CreateUser creatableUser(String email) {
    return new CreateUser()
        .firebaseId(randomUUID().toString())
        .profile(new UserProfile().email(email));
  }

  User from(CreateUser createUser, UserStatus status) {
    return new User()
        .firebaseId(createUser.getFirebaseId())
        .status(status)
        .profile(createUser.getProfile());
  }

  User updateProfile(User user) {
    return new User()
        .firebaseId(user.getFirebaseId())
        .profile(
            new UserProfile()
                .firstName(randomUUID().toString())
                .lastName(randomUUID().toString())
                .email(user.getProfile().getEmail()))
        .status(user.getStatus());
  }

  public User ignoreId(User user) {
    user.setId(null);
    return user;
  }
}
