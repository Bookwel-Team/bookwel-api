package api.prog5.bookwel.integration;

import static api.prog5.bookwel.endpoint.rest.model.ReactionStatus.LIKE;
import static api.prog5.bookwel.integration.mocks.MockData.USER_TWO_ID_TOKEN;
import static api.prog5.bookwel.integration.mocks.MockData.bookOne;
import static api.prog5.bookwel.integration.mocks.MockData.categoryCrupdateReaction;
import static api.prog5.bookwel.integration.mocks.MockData.crupdateReaction;
import static api.prog5.bookwel.integration.mocks.MockData.expectedCategoryReaction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @LocalServerPort
    private int serverPort;

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

        List<CategoryReaction> actualReactions =
                api.crupdateReactionsToCategories(List.of(categoryCrupdateReaction()));
        actualReactions.forEach(ReactionIT::resetIdAndCreationDatetime);

        assertTrue(actualReactions.contains(expectedCategoryReaction()));
    }

    private static void resetIdAndCreationDatetime(CategoryReaction categoryReaction) {
        categoryReaction.setId(null);
        categoryReaction.setCreationDatetime(null);
    }
}
