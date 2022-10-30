package com.github.dankook_univ.meetwork.post.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface PostRepository {

    Post save(Post post);

    Optional<Post> getById(Long postId);

    List<Post> getByBoardId(Long boardId, Pageable pageable);

    void delete(Post post);

    void deleteByWriterId(Long writerId);

    void deleteByBoardId(Long boardId);
}
