package api.prog5.bookwel.conf;

import org.springframework.test.context.DynamicPropertyRegistry;

public class BucketConf {

  void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("aws.s3.bucket", () -> "dummy-bucket");
    registry.add("aws.region", () -> "dummy-region");
  }
}
