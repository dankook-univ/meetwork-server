package com.github.dankook_univ.meetwork.post.comment.exceptions;

public class NotFoundCommentPermissionException extends RuntimeException {

    public NotFoundCommentPermissionException() {
        super("댓글에 대한 권한이 없습니다.");
    }
}
