package com.github.dankook_univ.meetwork.file.domain;

import java.util.List;
import java.util.Optional;

public interface FileRepository {

    Optional<File> getById(Long id);

    List<File> getByUploaderId(Long uploaderId);

    File save(File file);

    void delete(Long fileId);

    void delete(File file);
}
