package com.github.dankook_univ.meetwork.chat.domain.message;

import com.github.dankook_univ.meetwork.chat.domain.room.ChatRoom;
import com.github.dankook_univ.meetwork.chat.infra.http.response.ChatMessageResponse;
import com.github.dankook_univ.meetwork.common.domain.Core;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import io.jsonwebtoken.lang.Assert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
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

    @NotBlank
    private String message;

    @Builder
    public ChatMessage(ChatRoom room, Profile sender, String message) {
        Assert.isInstanceOf(ChatRoom.class, room, "room must be instance of ChatRoom");
        Assert.isInstanceOf(Profile.class, sender, "sender must be instance of Profile");
        Assert.hasText(message, "message must not be empty");

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
