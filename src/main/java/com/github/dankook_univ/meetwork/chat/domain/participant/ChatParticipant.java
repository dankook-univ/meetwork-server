package com.github.dankook_univ.meetwork.chat.domain.participant;

import com.github.dankook_univ.meetwork.chat.domain.room.ChatRoom;
import com.github.dankook_univ.meetwork.common.domain.Core;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import io.jsonwebtoken.lang.Assert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatParticipant extends Core {

    @ManyToOne(targetEntity = ChatRoom.class, fetch = FetchType.LAZY)
    private ChatRoom room;

    @ManyToOne(targetEntity = Profile.class, fetch = FetchType.LAZY)
    private Profile member;

    @Builder
    public ChatParticipant(ChatRoom room, Profile member) {
        Assert.isInstanceOf(ChatRoom.class, room, "room must be instance of ChatRoom");
        Assert.isInstanceOf(Profile.class, member, "member must be instance of Profile");

        this.room = room;
        this.member = member;
    }
}
