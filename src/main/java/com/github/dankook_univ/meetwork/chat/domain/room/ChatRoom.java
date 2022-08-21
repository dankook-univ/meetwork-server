package com.github.dankook_univ.meetwork.chat.domain.room;

import com.github.dankook_univ.meetwork.chat.domain.message.ChatMessage;
import com.github.dankook_univ.meetwork.chat.domain.participant.ChatParticipant;
import com.github.dankook_univ.meetwork.chat.infra.http.response.ChatRoomResponse;
import com.github.dankook_univ.meetwork.common.domain.Core;
import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class ChatRoom extends Core {

    @OneToMany(targetEntity = ChatMessage.class, mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<ChatMessage> messages = new ArrayList<>();
    @OneToMany(targetEntity = ChatParticipant.class, mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<ChatParticipant> participants = new ArrayList<>();
    @ManyToOne(targetEntity = Event.class, fetch = FetchType.LAZY)
    private Event event;

    @ManyToOne(targetEntity = Profile.class, fetch = FetchType.LAZY)
    private Profile organizer;

    private String name;

    private Boolean isPrivate;

    @Builder
    public ChatRoom(Event event, Profile organizer, String name, Boolean isPrivate) {
        this.event = event;
        this.organizer = organizer;
        this.name = name;
        this.isPrivate = isPrivate;
    }

    public ChatRoom update(@Nullable String name, @Nullable Boolean isPrivate) {
        if (name != null) {
            this.name = name;
        }
        if (isPrivate != null) {
            this.isPrivate = isPrivate;
        }

        return this;
    }

    public void appendMessage(ChatMessage message) {
        messages.add(message);
    }

    public void appendParticipant(ChatParticipant participant) {
        participants.add(participant);
    }

    public ChatRoomResponse toResponse() {
        return ChatRoomResponse.builder()
            .room(this)
            .build();
    }
}
