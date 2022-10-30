package com.github.dankook_univ.meetwork.invitation.application;

import com.github.dankook_univ.meetwork.invitation.domain.Invitation;
import com.github.dankook_univ.meetwork.invitation.infra.http.request.InvitationCreateRequest;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
import java.util.List;

public interface InvitationService {

    Boolean create(Long memberId, InvitationCreateRequest request);

    List<Invitation> getList(Long memberId);

    Boolean join(Long memberId, Long eventId, ProfileCreateRequest request);

    Boolean delete(Long memberId, Long eventId);
}
