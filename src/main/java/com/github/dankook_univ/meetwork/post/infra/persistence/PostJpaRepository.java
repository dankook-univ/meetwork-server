package com.github.dankook_univ.meetwork.post.infra.persistence;

import com.github.dankook_univ.meetwork.post.domain.Post;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, UUID> {

    Page<Post> findByBoardId(UUID boardId, Pageable pageable);
}
