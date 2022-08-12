package com.github.dankook_univ.meetwork.file.domain;

import java.util.Optional;

public interface FileRepository {

    Optional<File> getById(String id);

    File save(File file);

    void delete(String fileId);
}
