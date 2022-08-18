package com.github.dankook_univ.meetwork.chat.exceptions;

public class AlreadyChatRoomNameException extends IllegalStateException {

    public AlreadyChatRoomNameException() {
        super("채팅방 이름이 이미 존재합니다.");
    }
}
