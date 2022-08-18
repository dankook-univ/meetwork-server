package com.github.dankook_univ.meetwork.chat.infra.socket.response;

import com.github.dankook_univ.meetwork.chat.domain.message.ChatMessage;
import com.github.dankook_univ.meetwork.chat.domain.room.ChatRoom;
import com.github.dankook_univ.meetwork.event.infra.http.response.EventResponse;
import com.github.dankook_univ.meetwork.profile.infra.http.response.ProfileResponse;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageResponse {

    String id;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    EventResponse event;
    ProfileResponse sender;
    String message;

    @Builder
    public MessageResponse(ChatRoom room, ChatMessage message) {
        this.id = message.getId().toString();
        this.createdAt = message.getCreatedAt();
        this.updatedAt = message.getUpdatedAt();
        this.event = room.getEvent().toResponse();
        this.sender = message.getSender().toResponse();
        this.message = message.getMessage();
    }
}
