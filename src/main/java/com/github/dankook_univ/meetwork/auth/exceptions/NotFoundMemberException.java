package com.github.dankook_univ.meetwork.auth.exceptions;

public class NotFoundMemberException extends RuntimeException {

    public NotFoundMemberException() {
        super("회원정보가 존재하지 않습니다.");
    }
}
