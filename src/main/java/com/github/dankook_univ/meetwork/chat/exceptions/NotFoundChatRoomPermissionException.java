package com.github.dankook_univ.meetwork.chat.exceptions;

public class NotFoundChatRoomPermissionException extends IllegalAccessException {

    public NotFoundChatRoomPermissionException() {
        super("해당 채팅방에 권한이 없습니다.");
    }
}
