package com.github.dankook_univ.meetwork.event.application;

import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventCreateRequest;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventUpdateRequest;
import com.github.dankook_univ.meetwork.event.infra.http.request.ProfileReleaseRequest;
import com.github.dankook_univ.meetwork.event.infra.http.request.UpdateAdminRequest;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
import java.util.List;

public interface EventService {

    Event get(Long memberId, Long eventId);

    List<Event> getList(Long memberId, int page);

    List<Profile> getMemberList(Long memberId, Long eventId, Boolean adminOnly, int page);

    Profile getMember(Long memberId, Long eventId, Long profileId);

    Event create(Long memberId, EventCreateRequest request);

    Event update(Long memberId, Long profileId, EventUpdateRequest request);

    Boolean updateAdmin(Long memberId, UpdateAdminRequest request);

    Boolean checkExistingCode(String code);

    Event codeJoin(Long memberId, String code, ProfileCreateRequest request);

    Event join(Long memberId, Long eventId, ProfileCreateRequest request, Boolean isAdmin);

    Profile getMyProfile(Long memberId, Long eventId);

    void secession(Long memberId, Long eventId);

    void release(Long memberId, ProfileReleaseRequest request);

    void delete(Long memberId, Long eventId);
}
