package com.github.dankook_univ.meetwork.file.application.minio;

import com.github.dankook_univ.meetwork.file.exceptions.MinioStatObjectException;
import java.io.InputStream;

public interface MinioService {

    Boolean upload(String key, InputStream inputStream, Long size);

    Boolean delete(String key);

    Boolean stat(String key) throws MinioStatObjectException;
}
