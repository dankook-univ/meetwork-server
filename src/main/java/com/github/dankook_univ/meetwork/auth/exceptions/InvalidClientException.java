package com.github.dankook_univ.meetwork.auth.exceptions;

public class InvalidClientException extends RuntimeException {

    public InvalidClientException() {
        super("잘못된 접근입니다.");
    }
}
