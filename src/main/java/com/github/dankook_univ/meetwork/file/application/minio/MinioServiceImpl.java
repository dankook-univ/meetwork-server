package com.github.dankook_univ.meetwork.file.application.minio;

import com.github.dankook_univ.meetwork.file.exceptions.FileUploadFailedException;
import com.github.dankook_univ.meetwork.file.exceptions.MinioStatObjectException;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    public Boolean upload(String key, InputStream inputStream, Long size) {
        try {
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(key)
                    .stream(inputStream, size, -1)
                    .build()
            );

            return stat(key);
        } catch (Exception | MinioStatObjectException e) {
            log.error("[MinioService] 파일 업로드 오류: {}", e.getMessage());
            throw new FileUploadFailedException();
        }
    }

    public Boolean delete(String key) {
        try {
            minioClient.removeObject(
                RemoveObjectArgs.builder()
                    .bucket(bucket)
                    .object(key)
                    .build()
            );

            return true;
        } catch (Exception e) {
            log.error("[MinioService] 파일 삭제 오류: {}", e.getMessage());
            return false;
        }
    }

    public Boolean stat(String key) throws MinioStatObjectException {
        try {
            StatObjectResponse statObjectResponse = minioClient.statObject(
                StatObjectArgs.builder()
                    .bucket(bucket)
                    .object(key)
                    .build()
            );

            return statObjectResponse.size() > 0;
        } catch (Exception e) {
            throw new MinioStatObjectException();
        }
    }
}
