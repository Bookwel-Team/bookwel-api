package api.prog5.bookwel.integration;

import static api.prog5.bookwel.integration.mocks.MockData.USER_ONE_ID_TOKEN;
import static api.prog5.bookwel.integration.mocks.MockData.categoryOne;
import static api.prog5.bookwel.integration.mocks.MockData.categoryTwo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import api.prog5.bookwel.endpoint.rest.api.CategoryApi;
import api.prog5.bookwel.endpoint.rest.client.ApiClient;
import api.prog5.bookwel.endpoint.rest.client.ApiException;
import api.prog5.bookwel.endpoint.rest.model.Category;
import api.prog5.bookwel.integration.mocks.CustomFacadeIT;
import api.prog5.bookwel.utils.TestUtils;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;

public class CategoryIT extends CustomFacadeIT {
  @LocalServerPort private int serverPort;

  private ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, serverPort);
  }

  @Test
  void get_categories_ok() throws ApiException {
    ApiClient userOneClient = anApiClient(USER_ONE_ID_TOKEN);
    CategoryApi api = new CategoryApi(userOneClient);

    List<Category> actualCategories = api.getAllCategories(null);
    List<Category> actualCategoriesFiltered = api.getAllCategories(categoryTwo().getName());

    assertTrue(actualCategories.containsAll(List.of(categoryOne(), categoryTwo())));
    assertTrue(actualCategoriesFiltered.contains(categoryTwo()));
    assertEquals(1, actualCategoriesFiltered.size());
  }
}
