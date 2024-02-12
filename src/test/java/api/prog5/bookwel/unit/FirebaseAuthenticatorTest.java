package api.prog5.bookwel.unit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import api.prog5.bookwel.conf.FacadeIT;
import api.prog5.bookwel.endpoint.rest.exception.ForbiddenException;
import api.prog5.bookwel.endpoint.rest.security.auth.firebase.FirebaseAuthenticator;
import api.prog5.bookwel.endpoint.rest.security.auth.firebase.FirebaseConf;
import api.prog5.bookwel.service.AI.DataProcesser.api.recommendation.RecommendationAPI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class FirebaseAuthenticatorTest extends FacadeIT {
  @MockBean FirebaseConf firebaseConf;
  private static final String CORRECT_TOKEN = "correct_token";
  public static final String BAD_TOKEN = "bad_token";
  @Autowired FirebaseAuthenticator subject;
  @MockBean FirebaseAuth firebaseAuthMock;
  @MockBean RecommendationAPI recommendationAPIMock;
  FirebaseToken firebaseTokenMock = mock(FirebaseToken.class);

  @Test
  void get_firebase_token_ok() throws FirebaseAuthException {
    when(firebaseAuthMock.verifyIdToken(CORRECT_TOKEN)).thenReturn(firebaseTokenMock);

    assertDoesNotThrow(() -> subject.apply(CORRECT_TOKEN));
    assertDoesNotThrow(() -> subject.getEmail(CORRECT_TOKEN));
  }

  @Test
  void get_firebase_token_throws_ok() throws FirebaseAuthException {
    when(firebaseAuthMock.verifyIdToken(BAD_TOKEN)).thenThrow(FirebaseAuthException.class);

    assertThrows(ForbiddenException.class, () -> subject.apply(BAD_TOKEN));
  }
}
