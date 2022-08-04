package com.github.dankook_univ.meetwork.profile.exceptions;

public class NotFoundProfileException extends RuntimeException {

    public NotFoundProfileException() {
        super("프로필을 찾을 수 없습니다.");
    }
}

