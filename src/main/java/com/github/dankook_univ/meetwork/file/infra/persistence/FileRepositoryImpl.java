package com.github.dankook_univ.meetwork.file.infra.persistence;

import com.github.dankook_univ.meetwork.file.domain.File;
import com.github.dankook_univ.meetwork.file.domain.FileRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {

    final private FileJpaRepository fileJpaRepository;

    @Override
    public Optional<File> getById(Long id) {
        return fileJpaRepository.findById(id);
    }

    @Override
    public List<File> getByUploaderId(Long uploaderId) {
        return fileJpaRepository.findByUploaderId(uploaderId);
    }

    @Override
    public File save(File file) {
        return fileJpaRepository.save(file);
    }

    @Override
    public void delete(Long fileId) {
        fileJpaRepository.deleteById(fileId);
    }

    @Override
    public void delete(File file) {
        fileJpaRepository.delete(file);
    }
}
