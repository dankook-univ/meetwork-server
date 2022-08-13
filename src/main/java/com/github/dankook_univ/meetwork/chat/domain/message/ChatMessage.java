package com.github.dankook_univ.meetwork.chat.domain.message;

import com.github.dankook_univ.meetwork.chat.domain.room.ChatRoom;
import com.github.dankook_univ.meetwork.chat.infra.http.response.ChatMessageResponse;
import com.github.dankook_univ.meetwork.common.domain.Core;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage extends Core {

    @ManyToOne(targetEntity = ChatRoom.class, fetch = FetchType.LAZY)
    private ChatRoom room;

    @ManyToOne(targetEntity = Profile.class, fetch = FetchType.LAZY)
    private Profile sender;

    @NotEmpty
    @NotNull
    private String message;

    @Builder
    public ChatMessage(ChatRoom room, Profile sender, String message) {
        this.room = room;
        this.sender = sender;
        this.message = message;
    }

    public ChatMessageResponse toResponse() {
        return ChatMessageResponse.builder()
            .message(this)
            .build();
    }
}
