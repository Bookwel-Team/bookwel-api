package api.prog5.bookwel.service;

import static api.prog5.bookwel.endpoint.rest.exception.ApiException.ExceptionType.SERVER_EXCEPTION;
import static java.io.File.createTempFile;

import api.prog5.bookwel.endpoint.rest.exception.ApiException;
import java.io.File;
import java.io.IOException;
import java.util.function.Function;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Component
public class MultipartFileSaver implements Function<MultipartFile, File> {
    @Override
    public File apply(MultipartFile multipartFile) {
        try {
            File tempFile = createTempFile(multipartFile.getName(), null);
            multipartFile.transferTo(tempFile);
            return tempFile;
        } catch (IOException e) {
            throw new ApiException(SERVER_EXCEPTION, e.getMessage());
        }
    }
}
