package com.github.dankook_univ.meetwork.post.exceptions;

public class NotFoundPostPermissionException extends RuntimeException {

    public NotFoundPostPermissionException() {
        super("게시물에 대한 권한이 없습니다.");
    }
}
