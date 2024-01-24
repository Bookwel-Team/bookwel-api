package api.prog5.bookwel.endpoint.rest.security.auth.firebase;

import api.prog5.bookwel.endpoint.rest.security.exception.ForbiddenException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FirebaseAuthenticator implements Function<String, FirebaseToken> {

  private final FirebaseAuth firebaseAuth;

  @Override
  public FirebaseToken apply(String token) {
    return validateToken(token);
  }

  private FirebaseToken validateToken(String token) {
    try {
      return firebaseAuth.verifyIdToken(token);
    } catch (FirebaseAuthException e) {
      throw new ForbiddenException(e.getMessage());
    }
  }

  public String getEmail(String token) {
    return validateToken(token).getEmail();
  }
}
