package api.prog5.bookwel.integration.mocks;

import api.prog5.bookwel.endpoint.rest.api.AiApi;
import api.prog5.bookwel.endpoint.rest.client.ApiClient;
import api.prog5.bookwel.endpoint.rest.client.ApiResponse;
import api.prog5.bookwel.endpoint.rest.exception.ApiException;
import api.prog5.bookwel.endpoint.rest.model.AiResponse;

import java.util.Collections;
import java.util.List;

public class AiApiMock extends AiApi {

  @Override
  public List<AiResponse> chat(String body) throws ApiException {
    AiResponse dummyResponse = new AiResponse()
      .title("Dummy Title")
      .author("Dummy Author")
      .synopsis("Dummy Synopsis")
      .category("Dummy Category")
      .reason("Dummy Reason");

    return Collections.singletonList(dummyResponse);
  }

  @Override
  public ApiResponse<List<AiResponse>> chatWithHttpInfo(String body) throws ApiException {
    List<AiResponse> dummyList = Collections.singletonList(new AiResponse());
    return new ApiResponse<>(200, Collections.emptyMap(), dummyList);
  }

  public AiApiMock(ApiClient apiClient) {
    super(apiClient);
  }
}