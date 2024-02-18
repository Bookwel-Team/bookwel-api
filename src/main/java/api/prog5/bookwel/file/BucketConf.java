package api.prog5.bookwel.file;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class BucketConf {

  @Getter private final String bucketName;
  @Getter private final Region region;
  @Getter private final S3Presigner s3Presigner;

  public BucketConf(
      @Value("${aws.region}") String regionString, @Value("${aws.s3.bucket}") String bucketName) {
    this.bucketName = bucketName;
    this.region = Region.of(regionString);
    this.s3Presigner = S3Presigner.builder().region(region).build();
  }

  @Bean
  public S3Client getS3Client() {
    return S3Client.builder().region(region).build();
  }
}
