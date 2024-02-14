package api.prog5.bookwel.service.AI.DataProcesser.api.pdfReading;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class UnirestConfig {
  private com.fasterxml.jackson.databind.ObjectMapper mapper;

  @PostConstruct
  public void postConstruct() {
    Unirest.setTimeouts(0, 0);
    Unirest.setObjectMapper(
        new ObjectMapper() {

          public String writeValue(Object value) {
            try {
              return mapper.writeValueAsString(value);
            } catch (JsonProcessingException e) {
              throw new RuntimeException(e);
            }
          }

          public <T> T readValue(String value, Class<T> valueType) {
            try {
              return mapper.readValue(value, valueType);
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          }
        });
  }
}
