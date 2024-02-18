package api.prog5.bookwel.file;

import static api.prog5.bookwel.endpoint.rest.exception.ApiException.ExceptionType.SERVER_EXCEPTION;
import static api.prog5.bookwel.file.FileHashAlgorithm.SHA256;

import api.prog5.bookwel.Generated;
import api.prog5.bookwel.endpoint.rest.exception.ApiException;
import java.io.File;
import java.net.URL;
import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.internal.waiters.ResponseOrException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.ChecksumAlgorithm;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@Component
@AllArgsConstructor
@Generated
@Slf4j
public class BucketComponent {
  private final BucketConf bucketConf;

  public FileHash upload(File file, String bucketKey, MediaType mediaType) {
    PutObjectRequest request =
        PutObjectRequest.builder()
            .bucket(bucketConf.getBucketName())
            .contentType(mediaType.toString())
            .key(bucketKey)
            .checksumAlgorithm(ChecksumAlgorithm.SHA256)
            .build();

    PutObjectResponse objectResponse =
        bucketConf.getS3Client().putObject(request, RequestBody.fromFile(file));

    try (var waiter = bucketConf.getS3Client().waiter()) {
      ResponseOrException<HeadObjectResponse> responseOrException =
          waiter
              .waitUntilObjectExists(
                  HeadObjectRequest.builder()
                      .bucket(bucketConf.getBucketName())
                      .key(bucketKey)
                      .build())
              .matched();
      responseOrException
          .exception()
          .ifPresent(
              throwable -> {
                throw new ApiException(SERVER_EXCEPTION, throwable.getMessage());
              });
      responseOrException.response().ifPresent(response -> log.info("response={}", response));

      return new FileHash(SHA256, objectResponse.checksumSHA256());
    }
  }

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
