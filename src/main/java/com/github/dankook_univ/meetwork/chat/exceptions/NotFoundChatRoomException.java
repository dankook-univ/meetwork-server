package com.github.dankook_univ.meetwork.chat.exceptions;

public class NotFoundChatRoomException extends IllegalArgumentException {

    public NotFoundChatRoomException() {
        super("채팅방을 찾을 수 없습니다.");
    }
}
