package api.prog5.bookwel.endpoint.rest.mapper;

import static api.prog5.bookwel.endpoint.rest.exception.ApiException.ExceptionType.SERVER_EXCEPTION;
import static api.prog5.bookwel.endpoint.rest.model.UserStatus.ADMIN;
import static api.prog5.bookwel.endpoint.rest.model.UserStatus.CLIENT;

import api.prog5.bookwel.endpoint.rest.exception.ApiException;
import api.prog5.bookwel.endpoint.rest.model.CreateUser;
import api.prog5.bookwel.endpoint.rest.model.User;
import api.prog5.bookwel.endpoint.rest.model.UserProfile;
import api.prog5.bookwel.endpoint.rest.model.UserStatus;
import api.prog5.bookwel.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {
  private final UserRoleMapper roleMapper;
  private final UserService userService;

  public User toRest(api.prog5.bookwel.repository.model.User user) {
    return new User()
        .id(user.getId())
        .firebaseId(user.getFirebaseId())
        .status(roleMapper.toRest(user.getRole()))
        .profile(
            new UserProfile()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail()));
  }

  public api.prog5.bookwel.repository.model.User toDomain(CreateUser createUser, String id) {
    return api.prog5.bookwel.repository.model.User.builder()
        .id(id)
        .role(api.prog5.bookwel.repository.model.User.Role.CLIENT)
        .firebaseId(createUser.getFirebaseId())
        .build();
  }

  public api.prog5.bookwel.repository.model.User toDomain(UserProfile profile, String userId) {
    api.prog5.bookwel.repository.model.User persisted = userService.getById(userId);
    persisted.setEmail(profile.getEmail());
    persisted.setFirstName(profile.getFirstName());
    persisted.setLastName(profile.getLastName());
    return persisted;
  }

  @Component
  public static class UserRoleMapper {
    private UserStatus toRest(api.prog5.bookwel.repository.model.User.Role role) {
      return switch (role) {
        case ADMIN -> ADMIN;
        case CLIENT -> CLIENT;
        default -> throw new ApiException(
            SERVER_EXCEPTION, "unknown type from internal data " + role.name());
      };
    }
  }
}
