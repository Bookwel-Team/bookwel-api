package api.prog5.bookwel.integration.mocks;

import static api.prog5.bookwel.integration.mocks.MockData.UNKNOWN_USER_TOKEN;
import static api.prog5.bookwel.integration.mocks.MockData.USER_ONE_EMAIL;
import static api.prog5.bookwel.integration.mocks.MockData.USER_ONE_ID_TOKEN;
import static api.prog5.bookwel.integration.mocks.MockData.USER_TWO_EMAIL;
import static api.prog5.bookwel.integration.mocks.MockData.USER_TWO_ID_TOKEN;
import static api.prog5.bookwel.integration.mocks.MockData.dummyBookResponse;
import static api.prog5.bookwel.integration.mocks.MockData.likedBookOneMockAsAiBook;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import api.prog5.bookwel.conf.FacadeIT;
import api.prog5.bookwel.endpoint.rest.security.auth.firebase.FirebaseAuthenticator;
import api.prog5.bookwel.endpoint.rest.security.auth.firebase.FirebaseConf;
import api.prog5.bookwel.file.BucketComponent;
import api.prog5.bookwel.service.AI.DataProcesser.api.pdfReading.PdfReadingAPI;
import api.prog5.bookwel.service.AI.DataProcesser.api.recommendation.RecommendationAPI;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;

public class CustomFacadeIT extends FacadeIT {
  @MockBean protected FirebaseConf firebaseConfMock;
  @MockBean protected FirebaseApp firebaseAppMock;
  @MockBean protected FirebaseAuth firebaseAuthMock;
  @MockBean protected FirebaseAuthenticator firebaseAuthenticator;
  @MockBean protected BucketComponent bucketComponent;
  @MockBean protected RecommendationAPI recommendationAPIMock;
  @MockBean protected PdfReadingAPI pdfReadingAPIMock;

  protected void setupFirebaseAuthenticator(FirebaseAuthenticator firebaseAuthenticator) {
    when(firebaseAuthenticator.getEmail(USER_ONE_ID_TOKEN)).thenReturn(USER_ONE_EMAIL);
    when(firebaseAuthenticator.getEmail(USER_TWO_ID_TOKEN)).thenReturn(USER_TWO_EMAIL);
    when(firebaseAuthenticator.getEmail(UNKNOWN_USER_TOKEN)).thenReturn(null);
  }

  protected void setupBucketComponent(BucketComponent bucketComponent)
      throws MalformedURLException {
    when(bucketComponent.presign(any(), any())).thenReturn(new URL("http://localhost"));
  }

  protected void setupRecommendationApi(RecommendationAPI recommendationAPI) {
    // return all books without filtering
    when(recommendationAPI.apply(anyList(), anyList(), anyList()))
        .thenAnswer((i) -> List.of(likedBookOneMockAsAiBook()));
  }

  protected void setupPdfReadingApiMock(PdfReadingAPI pdfReadingAPI) {
    when(pdfReadingAPI.apply(any())).thenReturn(dummyBookResponse());
  }

  @BeforeEach
  void setup() throws MalformedURLException {
    setupFirebaseAuthenticator(firebaseAuthenticator);
    setupBucketComponent(bucketComponent);
    setupRecommendationApi(recommendationAPIMock);
    setupPdfReadingApiMock(pdfReadingAPIMock);
  }
}
