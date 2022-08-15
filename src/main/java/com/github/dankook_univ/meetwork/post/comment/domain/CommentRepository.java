package com.github.dankook_univ.meetwork.post.comment.domain;

import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> getById(String commentId);

    void delete(Comment comment);
}
