package api.prog5.bookwel.endpoint.rest.security.auth;

import api.prog5.bookwel.endpoint.rest.exception.NotFoundException;
import api.prog5.bookwel.endpoint.rest.security.auth.firebase.FirebaseAuthenticator;
import api.prog5.bookwel.endpoint.rest.security.model.Principal;
import api.prog5.bookwel.repository.model.User;
import api.prog5.bookwel.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class AuthProvider extends AbstractUserDetailsAuthenticationProvider {
  private final UserService userService;
  private final FirebaseAuthenticator firebaseAuthenticator;
  private static final String BEARER_PREFIX = "Bearer ";

  @Override
  protected void additionalAuthenticationChecks(
      UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
      throws AuthenticationException {
    // nothing
  }

  @Override
  protected UserDetails retrieveUser(
      String username, UsernamePasswordAuthenticationToken authentication)
      throws AuthenticationException {
    String bearer = getBearerFromHeader(authentication);
    if (bearer == null) {
      throw new UsernameNotFoundException("bad credentials");
    }
    String email = firebaseAuthenticator.getEmail(bearer);
    if (email == null) {
      throw new UsernameNotFoundException("bad credentials");
    }
    try {
      User user = userService.getByEmail(email);
      return new Principal(user, bearer);
    } catch (NotFoundException e) {
      throw new UsernameNotFoundException("bad credentials");
    }
  }

  private String getBearerFromHeader(
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
    Object tokenObject = usernamePasswordAuthenticationToken.getCredentials();
    if (!(tokenObject instanceof String) || !((String) tokenObject).startsWith(BEARER_PREFIX)) {
      return null;
    }
    return ((String) tokenObject).substring(BEARER_PREFIX.length()).trim();
  }

  public static Principal getPrincipal() {
    SecurityContext context = SecurityContextHolder.getContext();
    Authentication authentication = context.getAuthentication();
    return (Principal) authentication.getPrincipal();
  }
}
