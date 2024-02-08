package api.prog5.bookwel.file;

import static api.prog5.bookwel.endpoint.rest.exception.ApiException.ExceptionType.SERVER_EXCEPTION;
import static api.prog5.bookwel.file.FileHashAlgorithm.NONE;
import static api.prog5.bookwel.file.FileHashAlgorithm.SHA256;

import api.prog5.bookwel.Generated;
import api.prog5.bookwel.endpoint.rest.exception.ApiException;
import java.io.File;
import java.net.URL;
import java.time.Duration;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.transfer.s3.model.UploadDirectoryRequest;
import software.amazon.awssdk.transfer.s3.model.UploadFileRequest;
import software.amazon.awssdk.transfer.s3.progress.LoggingTransferListener;

@Component
@AllArgsConstructor
@Generated
public class BucketComponent {
  private final BucketConf bucketConf;

  public FileHash upload(File file, String bucketKey) {
    return file.isDirectory() ? uploadDirectory(file, bucketKey) : uploadFile(file, bucketKey);
  }

  private FileHash uploadDirectory(File file, String bucketKey) {
    var request =
        UploadDirectoryRequest.builder()
            .source(file.toPath())
            .bucket(bucketConf.getBucketName())
            .s3Prefix(bucketKey)
            .build();
    var upload = bucketConf.getS3TransferManager().uploadDirectory(request);
    var uploaded = upload.completionFuture().join();
    if (!uploaded.failedTransfers().isEmpty()) {
      throw new ApiException(
          SERVER_EXCEPTION, "Failed to upload following files: " + uploaded.failedTransfers());
    }
    return new FileHash(NONE, null);
  }

  private FileHash uploadFile(File file, String bucketKey) {
    var request =
        UploadFileRequest.builder()
            .source(file)
            .putObjectRequest(req -> req.bucket(bucketConf.getBucketName()).key(bucketKey))
            .addTransferListener(LoggingTransferListener.create())
            .build();
    var upload = bucketConf.getS3TransferManager().uploadFile(request);
    var uploaded = upload.completionFuture().join();
    return new FileHash(SHA256, uploaded.response().checksumSHA256());
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
