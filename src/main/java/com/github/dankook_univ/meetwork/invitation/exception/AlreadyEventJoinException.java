package com.github.dankook_univ.meetwork.invitation.exception;

public class AlreadyEventJoinException extends RuntimeException {

    public AlreadyEventJoinException() {
        super("이미 이벤트에 참여하고 있습니다.");
    }

}
