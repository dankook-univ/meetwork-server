package com.github.dankook_univ.meetwork.invitation.infra.http.request;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class InvitationCreateRequest {

    String eventId;

    List<InvitationInformation> invitationInformations;
}
