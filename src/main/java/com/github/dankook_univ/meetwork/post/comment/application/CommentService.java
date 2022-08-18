package com.github.dankook_univ.meetwork.post.comment.application;

import com.github.dankook_univ.meetwork.post.comment.domain.Comment;
import com.github.dankook_univ.meetwork.post.comment.infra.http.request.CommentCreateRequest;
import com.github.dankook_univ.meetwork.post.comment.infra.http.request.CommentUpdateRequest;

public interface CommentService {

    Comment create(String memberId, CommentCreateRequest request);

    Comment update(String memberId, String commentId, CommentUpdateRequest request);

    void delete(String memberId, String commentId);
}
