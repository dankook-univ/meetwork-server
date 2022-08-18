package com.github.dankook_univ.meetwork.chat.infra.http.response;

import com.github.dankook_univ.meetwork.chat.domain.message.ChatMessage;
import com.github.dankook_univ.meetwork.profile.infra.http.response.ProfileResponse;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatMessageResponse {

    String id;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    ChatRoomResponse room;
    ProfileResponse sender;
    String message;

    @Builder
    public ChatMessageResponse(ChatMessage message) {
        this.id = message.getId().toString();
        this.createdAt = message.getCreatedAt();
        this.updatedAt = message.getUpdatedAt();
        this.room = message.getRoom().toResponse();
        this.sender = message.getSender().toResponse();
        this.message = message.getMessage();
    }
}
