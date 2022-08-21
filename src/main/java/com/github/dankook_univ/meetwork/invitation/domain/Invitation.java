package com.github.dankook_univ.meetwork.invitation.domain;

import com.github.dankook_univ.meetwork.common.domain.Core;
import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.invitation.infra.http.response.InvitationResponse;
import com.github.dankook_univ.meetwork.member.domain.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Invitation extends Core {

    @ManyToOne(targetEntity = Event.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id")
    private Member guest;

    @Column(nullable = false)
    private Boolean isAdmin;

    @Builder
    public Invitation(Event event, Member guest, Boolean isAdmin) {
        Assert.notNull(event, "event must not be null");
        Assert.notNull(guest, "guest must not be null");
        Assert.notNull(isAdmin, "isAdmin must not be null");

        this.event = event;
        this.guest = guest;
        this.isAdmin = isAdmin;
    }

    public InvitationResponse toResponse() {
        return InvitationResponse.builder()
            .invitation(this)
            .build();
    }
}
