package com.github.dankook_univ.meetwork.post.infra.persistence;

import com.github.dankook_univ.meetwork.post.domain.Post;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostJpaRepository extends JpaRepository<Post, UUID> {

    Page<Post> findByBoardIdOrderByUpdatedAtDesc(UUID boardId, Pageable pageable);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Post p where p.writer.id = :writerId")
    void deleteAllByWriterId(UUID writerId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Post p where p.board.id = :boardId")
    void deleteAllByBoardId(UUID boardId);

}
