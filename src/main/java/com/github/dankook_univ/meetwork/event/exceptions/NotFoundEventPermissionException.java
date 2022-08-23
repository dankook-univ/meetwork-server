package com.github.dankook_univ.meetwork.event.exceptions;

public class NotFoundEventPermissionException extends IllegalArgumentException {

    public NotFoundEventPermissionException() {
        super("해당 이벤트에 대한 권한이 없습니다.");
    }
}
