package com.github.dankook_univ.meetwork.invitation.exception;

public class NotFoundInvitationException extends RuntimeException {

    public NotFoundInvitationException() {
        super("해당 초대를 찾을 수 없습니다.");
    }
}
