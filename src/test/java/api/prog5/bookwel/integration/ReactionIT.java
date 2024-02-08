package api.prog5.bookwel.integration;

import static api.prog5.bookwel.endpoint.rest.model.ReactionStatus.DISLIKE;
import static api.prog5.bookwel.endpoint.rest.model.ReactionStatus.LIKE;
import static api.prog5.bookwel.integration.mocks.MockData.USER_TWO_ID_TOKEN;
import static api.prog5.bookwel.integration.mocks.MockData.bookOne;
import static api.prog5.bookwel.integration.mocks.MockData.bookReaction1;
import static api.prog5.bookwel.integration.mocks.MockData.bookReaction3;
import static api.prog5.bookwel.integration.mocks.MockData.categoryOne;
import static api.prog5.bookwel.integration.mocks.MockData.categoryReaction1;
import static api.prog5.bookwel.integration.mocks.MockData.categoryReaction3;
import static api.prog5.bookwel.integration.mocks.MockData.crupdateReaction;
import static api.prog5.bookwel.integration.mocks.MockData.expectedCategoryReaction;
import static org.junit.jupiter.api.Assertions.assertEquals;

import api.prog5.bookwel.endpoint.rest.api.ReactionApi;
import api.prog5.bookwel.endpoint.rest.client.ApiClient;
import api.prog5.bookwel.endpoint.rest.client.ApiException;
import api.prog5.bookwel.endpoint.rest.model.BookReaction;
import api.prog5.bookwel.endpoint.rest.model.CategoryReaction;
import api.prog5.bookwel.integration.mocks.CustomFacadeIT;
import api.prog5.bookwel.utils.TestUtils;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class ReactionIT extends CustomFacadeIT {
  @LocalServerPort private int serverPort;

  private ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, serverPort);
  }

  @Test
  void crupdate_reaction() throws ApiException {
    ApiClient userTwoClient = anApiClient(USER_TWO_ID_TOKEN);
    ReactionApi api = new ReactionApi(userTwoClient);

    BookReaction actual = api.crupdateReactionToABook(bookOne().getId(), crupdateReaction());

    assertEquals(LIKE, actual.getReactionStatus());
  }

  @Test
  void crupdate_category_reaction() throws ApiException {
    ApiClient userTwoClient = anApiClient(USER_TWO_ID_TOKEN);
    ReactionApi api = new ReactionApi(userTwoClient);

    CategoryReaction categoryReaction =
        api.crupdateReactionToACategory(categoryOne().getId(), crupdateReaction());
    categoryReaction.setId(null);
    categoryReaction.setCreationDatetime(null);

    assertEquals(expectedCategoryReaction(), categoryReaction);
  }

  @Test
  void read_category_reactions_ok() throws ApiException {
    ApiClient userTwoClient = anApiClient(USER_TWO_ID_TOKEN);
    ReactionApi api = new ReactionApi(userTwoClient);

    List<CategoryReaction> actualReactionsToCategoryOne =
        api.getReactionsToCategory(categoryOne().getId(), null);
    List<CategoryReaction> actualLikesToCategoryOne =
        api.getReactionsToCategory(categoryOne().getId(), LIKE);
    List<CategoryReaction> actualDislikesToCategoryOne =
        api.getReactionsToCategory(categoryOne().getId(), DISLIKE);

    assertEquals(List.of(categoryReaction1(), categoryReaction3()), actualReactionsToCategoryOne);
    assertEquals(List.of(categoryReaction1(), categoryReaction3()), actualLikesToCategoryOne);
    assertEquals(List.of(), actualDislikesToCategoryOne);
  }

  @Test
  void read_book_reactions_ok() throws ApiException {
    ApiClient userTwoClient = anApiClient(USER_TWO_ID_TOKEN);
    ReactionApi api = new ReactionApi(userTwoClient);

    List<BookReaction> actualReactionsToBookOne = api.getReactionsToABook(bookOne().getId(), null);
    List<BookReaction> actualLikesToBookOne = api.getReactionsToABook(bookOne().getId(), LIKE);
    List<BookReaction> actualDislikesToBookOne =
        api.getReactionsToABook(bookOne().getId(), DISLIKE);

    assertEquals(List.of(bookReaction1(), bookReaction3()), actualReactionsToBookOne);
    assertEquals(List.of(bookReaction1()), actualLikesToBookOne);
    assertEquals(List.of(bookReaction3()), actualDislikesToBookOne);
  }
}
