package api.prog5.bookwel.endpoint.rest.security.auth;

import api.prog5.bookwel.repository.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenticatedResourceProvider {
  public User getAuthenticatedUser() {
    return AuthProvider.getPrincipal().getUser();
  }
}
