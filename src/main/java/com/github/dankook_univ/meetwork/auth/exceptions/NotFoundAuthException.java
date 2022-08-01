package com.github.dankook_univ.meetwork.auth.exceptions;

public class NotFoundAuthException extends RuntimeException {

    public NotFoundAuthException() {
        super("회원정보가 존재하지 않습니다.");
    }
}
