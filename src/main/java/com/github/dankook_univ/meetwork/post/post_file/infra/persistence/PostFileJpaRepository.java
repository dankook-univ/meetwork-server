package com.github.dankook_univ.meetwork.post.post_file.infra.persistence;

import com.github.dankook_univ.meetwork.post.post_file.domain.PostFile;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostFileJpaRepository extends JpaRepository<PostFile, UUID> {

    List<PostFile> findByPostId(UUID postId);
}
