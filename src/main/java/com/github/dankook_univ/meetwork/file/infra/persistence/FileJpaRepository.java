package com.github.dankook_univ.meetwork.file.infra.persistence;

import com.github.dankook_univ.meetwork.file.domain.File;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileJpaRepository extends JpaRepository<File, UUID> {

}
