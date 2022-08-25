package com.github.dankook_univ.meetwork.invitation.infra.http.response;

import com.github.dankook_univ.meetwork.event.infra.http.response.EventResponse;
import com.github.dankook_univ.meetwork.invitation.domain.Invitation;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InvitationResponse {

    @NotBlank
    String id;

    @NotNull
    EventResponse event;

    @NotNull
    Boolean isAdmin;

    @NotBlank
    LocalDateTime createAt;

    @NotBlank
    LocalDateTime updateAt;

    @Builder
    public InvitationResponse(
        Invitation invitation
    ) {
        this.id = invitation.getId().toString();
        this.event = invitation.getEvent().toResponse();
        this.isAdmin = invitation.getIsAdmin();
        this.createAt = invitation.getCreatedAt();
        this.updateAt = invitation.getUpdatedAt();
    }
}
