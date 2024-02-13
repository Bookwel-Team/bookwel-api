package api.prog5.bookwel.conf;

import org.springframework.test.context.DynamicPropertyRegistry;

public class EnvConf {
  void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.flyway.locations", () -> "classpath:/db/migration,classpath:/db/testData");
    registry.add("pdf.reading.api.url", () -> "http://localhost.com");
  }
}
