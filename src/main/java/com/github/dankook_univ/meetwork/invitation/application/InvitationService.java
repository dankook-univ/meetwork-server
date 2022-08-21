package com.github.dankook_univ.meetwork.invitation.application;

import com.github.dankook_univ.meetwork.invitation.domain.Invitation;
import com.github.dankook_univ.meetwork.invitation.infra.http.request.InvitationCreateRequest;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
import java.util.List;

public interface InvitationService {

    Boolean create(String memberId, InvitationCreateRequest request);

    List<Invitation> getList(String memberId);

    Boolean join(String memberId, String eventId, ProfileCreateRequest request);

    Boolean delete(String memberId, String eventId);
}
