package api.prog5.bookwel.integration.mocks;

import static api.prog5.bookwel.integration.mocks.MockData.USER_ONE_EMAIL;
import static api.prog5.bookwel.integration.mocks.MockData.USER_ONE_ID_TOKEN;
import static api.prog5.bookwel.integration.mocks.MockData.USER_TWO_EMAIL;
import static api.prog5.bookwel.integration.mocks.MockData.USER_TWO_ID_TOKEN;
import static org.mockito.Mockito.when;

import api.prog5.bookwel.conf.FacadeIT;
import api.prog5.bookwel.endpoint.rest.security.auth.firebase.FirebaseAuthenticator;
import api.prog5.bookwel.endpoint.rest.security.auth.firebase.FirebaseConf;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.boot.test.mock.mockito.MockBean;

public class CustomFacadeIT extends FacadeIT {
  @MockBean protected FirebaseConf firebaseConfMock;
  @MockBean protected FirebaseApp firebaseAppMock;
  @MockBean protected FirebaseAuth firebaseAuthMock;
  @MockBean protected FirebaseAuthenticator firebaseAuthenticator;

  protected void setupFirebaseAuthenticator(FirebaseAuthenticator firebaseAuthenticator) {
    when(firebaseAuthenticator.getEmail(USER_ONE_ID_TOKEN)).thenReturn(USER_ONE_EMAIL);
    when(firebaseAuthenticator.getEmail(USER_TWO_ID_TOKEN)).thenReturn(USER_TWO_EMAIL);
  }
}
