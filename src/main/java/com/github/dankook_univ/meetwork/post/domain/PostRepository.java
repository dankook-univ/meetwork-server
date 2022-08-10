package com.github.dankook_univ.meetwork.post.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface PostRepository {

    Post save(Post post);

    Optional<Post> getById(UUID postId);

    List<Post> getByBoardId(UUID boardId, Pageable pageable);

    void delete(Post post);

}
