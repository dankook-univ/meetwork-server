package com.github.dankook_univ.meetwork.chat.exceptions;

public class NotParticipatedMemberException extends IllegalAccessException {

    public NotParticipatedMemberException() {
        super("채팅방에 참여중이지 않습니다.");
    }
}
