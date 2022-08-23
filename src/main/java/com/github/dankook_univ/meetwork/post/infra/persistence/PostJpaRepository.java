package com.github.dankook_univ.meetwork.post.infra.persistence;

import com.github.dankook_univ.meetwork.post.domain.Post;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostJpaRepository extends JpaRepository<Post, UUID> {

    Page<Post> findByBoardId(UUID boardId, Pageable pageable);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Post p where p.writer.id = :writerId")
    void deleteByWriterId(UUID writerId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Post p where p.board.id = :boardId")
    void deleteByBoardId(UUID boardId);

}
