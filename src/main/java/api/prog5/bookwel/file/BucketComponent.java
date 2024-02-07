package api.prog5.bookwel.file;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.net.URL;
import java.time.Duration;

@Component
@AllArgsConstructor
public class BucketComponent {
    private final BucketConf bucketConf;

    public URL presign(String bucketKey, Duration expiration) {
        GetObjectRequest getObjectRequest =
                GetObjectRequest.builder().bucket(bucketConf.getBucketName()).key(bucketKey).build();
        PresignedGetObjectRequest presignedRequest =
                bucketConf
                        .getS3Presigner()
                        .presignGetObject(
                                GetObjectPresignRequest.builder()
                                        .signatureDuration(expiration)
                                        .getObjectRequest(getObjectRequest)
                                        .build());
        return presignedRequest.url();
    }
}
