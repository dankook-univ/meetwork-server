package com.github.dankook_univ.meetwork.post.comment.exceptions;

public class NotFoundCommentException extends RuntimeException {

    public NotFoundCommentException() {
        super("댓글이 존재하지 않습니다.");
    }

}
