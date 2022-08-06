package com.github.dankook_univ.meetwork.file.infra.minio;

import com.github.dankook_univ.meetwork.config.MinioConfig;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;

    private final String bucket = MinioConfig.bucket;

    public Boolean upload(String key, InputStream inputStream, Long size, String mime) {
        try {
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(key)
                    .stream(inputStream, size, -1)
                    .contentType(mime)
                    .build()
            );
            return stat(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
            e.printStackTrace();
            return false;
        }
    }

    public Boolean stat(String key) {
        try {
            StatObjectResponse statObjectResponse = minioClient.statObject(
                StatObjectArgs.builder()
                    .bucket(bucket)
                    .object(key)
                    .build()
            );

            return statObjectResponse.size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
