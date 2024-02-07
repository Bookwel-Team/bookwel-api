package api.prog5.bookwel.integration;

import static api.prog5.bookwel.endpoint.rest.model.UserStatus.CLIENT;
import static api.prog5.bookwel.integration.mocks.MockData.USER_ONE_ID_TOKEN;
import static api.prog5.bookwel.integration.mocks.MockData.userOne;
import static api.prog5.bookwel.integration.mocks.MockData.userTwo;
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

    assertTrue(users.containsAll(List.of(userOne(), userTwo())));
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
  void crupdate_user_ok() throws ApiException {
    ApiClient userOneClient = anApiClient(USER_ONE_ID_TOKEN);
    UsersApi api = new UsersApi(userOneClient);
    CreateUser payload = creatableUser();
    User expected = from(payload, CLIENT);

    User actual = api.crupdateUser(payload.getId(), payload);
    User updatedPayload = setProfile(actual);
    User actualUpdated = api.updateUserProfile(actual.getId(), updatedPayload.getProfile());

    assertEquals(expected, actual);
    assertEquals(updatedPayload, actualUpdated);
  }

  CreateUser creatableUser(){
    return new CreateUser()
            .id(randomUUID().toString())
            .firebaseId(randomUUID().toString());
  }
  User from(CreateUser createUser, UserStatus status){
    return new User()
            .id(createUser.getId())
            .firebaseId(createUser.getFirebaseId())
            .status(status)
            .profile(new UserProfile());
  }
  User setProfile(User user){
      return new User()
              .id(user.getId())
              .firebaseId(user.getFirebaseId())
              .profile(new UserProfile()
                      .firstName(randomUUID().toString())
                      .lastName(randomUUID().toString())
                      .email("test@email.com")
              )
              .status(user.getStatus());
  }
}
