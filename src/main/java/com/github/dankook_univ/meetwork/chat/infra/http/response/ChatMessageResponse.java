package com.github.dankook_univ.meetwork.chat.infra.http.response;

import com.github.dankook_univ.meetwork.chat.domain.message.ChatMessage;
import com.github.dankook_univ.meetwork.profile.infra.http.response.ProfileResponse;
import lombok.Builder;

public class ChatMessageResponse {

    ChatRoomResponse room;
    ProfileResponse sender;
    String message;

    @Builder

    public ChatMessageResponse(ChatMessage message) {
        this.room = message.getRoom().toResponse();
        this.sender = message.getSender().toResponse();
        this.message = message.getMessage();
    }
}
