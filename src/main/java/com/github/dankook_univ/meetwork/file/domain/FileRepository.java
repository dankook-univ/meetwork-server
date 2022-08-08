package com.github.dankook_univ.meetwork.file.domain;

import java.util.Optional;
import java.util.UUID;

public interface FileRepository {

	Optional<File> getById(UUID id);

	File save(File file);

	void delete(UUID fileId);
}
