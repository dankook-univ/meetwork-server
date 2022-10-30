package com.github.dankook_univ.meetwork.post.comment.application;

import com.github.dankook_univ.meetwork.post.comment.domain.Comment;
import com.github.dankook_univ.meetwork.post.comment.infra.http.request.CommentCreateRequest;
import com.github.dankook_univ.meetwork.post.comment.infra.http.request.CommentUpdateRequest;

public interface CommentService {

    Comment create(Long memberId, CommentCreateRequest request);

    Comment update(Long memberId, Long commentId, CommentUpdateRequest request);

    void delete(Long memberId, Long commentId);
}
