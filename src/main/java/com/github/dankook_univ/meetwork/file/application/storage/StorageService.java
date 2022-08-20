package com.github.dankook_univ.meetwork.file.application.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void upload(MultipartFile file, String key);

    void delete(String key);
}
