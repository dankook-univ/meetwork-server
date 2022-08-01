package com.github.dankook_univ.meetwork.auth.exceptions;

public class ExistingAuthException extends RuntimeException {

    public ExistingAuthException() {
        super("인증 정보가 이미 존재합니다.");
    }
}
