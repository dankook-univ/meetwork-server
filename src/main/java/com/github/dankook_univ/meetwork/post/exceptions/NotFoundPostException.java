package com.github.dankook_univ.meetwork.post.exceptions;

public class NotFoundPostException extends RuntimeException {

    public NotFoundPostException() {
        super("게시물을 찾을 수 없습니다.");
    }

}
