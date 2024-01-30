package api.prog5.bookwel.endpoint.rest.security.matcher;

import api.prog5.bookwel.endpoint.rest.security.auth.AuthenticatedResourceProvider;
import org.springframework.http.HttpMethod;

public class SelfUserMatcher extends SelfMatcher {
  public SelfUserMatcher(
      HttpMethod method, String antPattern, AuthenticatedResourceProvider authResourceProvider) {
    super(method, antPattern, authResourceProvider);
  }

  @Override
  protected String getAccessibleProtectedResourceId() {
    return authResourceProvider.getAuthenticatedUser().getId();
  }
}
