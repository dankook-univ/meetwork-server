package com.github.dankook_univ.meetwork.chat.infra.http.response;

import com.github.dankook_univ.meetwork.chat.domain.participant.ChatParticipant;
import com.github.dankook_univ.meetwork.chat.domain.room.ChatRoom;
import com.github.dankook_univ.meetwork.event.infra.http.response.EventResponse;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.infra.http.response.ProfileResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomResponse {

    String id;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String name;
    Boolean isPrivate;
    ProfileResponse organizer;
    EventResponse event;
    List<ProfileResponse> participants;

    @Builder
    public ChatRoomResponse(ChatRoom room) {
        this.id = room.getId().toString();
        this.createdAt = room.getCreatedAt();
        this.updatedAt = room.getUpdatedAt();
        this.name = room.getName();
        this.isPrivate = room.getIsPrivate();
        this.organizer = room.getOrganizer().toResponse();
        this.event = room.getEvent().toResponse();
        this.participants = room.getParticipants().stream()
            .map(ChatParticipant::getMember)
            .map(Profile::toResponse)
            .collect(Collectors.toList());
    }
}
