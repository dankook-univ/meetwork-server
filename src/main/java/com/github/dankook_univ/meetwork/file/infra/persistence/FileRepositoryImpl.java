package com.github.dankook_univ.meetwork.file.infra.persistence;

import com.github.dankook_univ.meetwork.file.domain.File;
import com.github.dankook_univ.meetwork.file.domain.FileRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {

    final private FileJpaRepository fileJpaRepository;

    @Override
    public Optional<File> getById(String id) {
        return fileJpaRepository.findById(UUID.fromString(id));
    }

    @Override
    public List<File> getByUploaderId(String uploaderId) {
        return fileJpaRepository.findByUploaderId(UUID.fromString(uploaderId));
    }

    @Override
    public File save(File file) {
        return fileJpaRepository.save(file);
    }

    @Override
    public void delete(String fileId) {
        fileJpaRepository.deleteById(UUID.fromString(fileId));
    }

    @Override
    public void delete(File file) {
        fileJpaRepository.delete(file);
    }
}
