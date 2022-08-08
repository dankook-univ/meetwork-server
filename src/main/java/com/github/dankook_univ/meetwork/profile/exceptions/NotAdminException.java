package com.github.dankook_univ.meetwork.profile.exceptions;

public class NotAdminException extends RuntimeException {

    public NotAdminException() {
        super("행사의 관리자가 아닙니다.");
    }
}
