package com.github.dankook_univ.meetwork.invitation.infra.http.response;

import com.github.dankook_univ.meetwork.event.infra.http.response.EventResponse;
import com.github.dankook_univ.meetwork.invitation.domain.Invitation;
import com.github.dankook_univ.meetwork.member.infra.http.response.MemberResponse;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InvitationResponse {

    @NotNull
    @NotEmpty
    String id;

    @NotNull
    EventResponse event;

    @NotNull
    MemberResponse member;

    @NotNull
    Boolean isAdmin;

    @NotNull
    @NotEmpty
    LocalDateTime createAt;

    @NotNull
    @NotEmpty
    LocalDateTime updateAt;

    @Builder
    public InvitationResponse(
        Invitation invitation
    ) {
        this.id = invitation.getId().toString();
        this.event = invitation.getEvent().toResponse();
        this.member = invitation.getGuest().toResponse();
        this.isAdmin = invitation.getIsAdmin();
        this.createAt = invitation.getCreatedAt();
        this.updateAt = invitation.getUpdatedAt();
    }
}