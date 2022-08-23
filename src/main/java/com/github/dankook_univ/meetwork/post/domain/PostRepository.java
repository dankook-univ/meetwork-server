package com.github.dankook_univ.meetwork.post.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface PostRepository {

    Post save(Post post);

    Optional<Post> getById(String postId);

    List<Post> getByBoardId(String boardId, Pageable pageable);

    void delete(Post post);

    void deleteByWriterId(String writerId);

    void deleteByBoardId(String boardId);
}
