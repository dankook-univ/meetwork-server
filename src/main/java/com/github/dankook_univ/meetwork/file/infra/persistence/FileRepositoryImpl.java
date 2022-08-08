package com.github.dankook_univ.meetwork.file.infra.persistence;

import com.github.dankook_univ.meetwork.file.domain.File;
import com.github.dankook_univ.meetwork.file.domain.FileRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {

	final private FileJpaRepository fileJpaRepository;

	@Override
	public Optional<File> getById(UUID id) {
		return fileJpaRepository.findById(id);
	}

	@Override
	public File save(File file) {
		return fileJpaRepository.save(file);
	}

	@Override
	public void delete(UUID fileId) {
		fileJpaRepository.deleteById(fileId);
	}
}
