package com.github.dankook_univ.meetwork.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.access_key}")
    private String access_key;

    @Value("${minio.secret_key}")
    private String secret_key;

    @Value("${minio.bucket}")
    public static String bucket;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
            .endpoint(endpoint)
            .credentials(access_key, secret_key)
            .build();
    }
}
