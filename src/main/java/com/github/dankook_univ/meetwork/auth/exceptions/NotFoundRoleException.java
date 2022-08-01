package com.github.dankook_univ.meetwork.auth.exceptions;

public class NotFoundRoleException extends RuntimeException {

    public NotFoundRoleException() {
        super("권한 정보가 없습니다.");
    }
}
