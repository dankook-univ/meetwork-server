package com.github.dankook_univ.meetwork.event.application;

import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventCreateRequest;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventUpdateRequest;
import com.github.dankook_univ.meetwork.event.infra.http.request.ProfileReleaseRequest;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
import java.util.List;

public interface EventService {

    Event get(String memberId, String eventId);

    List<Event> getList(String memberId, int page);

    List<Profile> getMemberList(String memberId, String eventId, int page);

    Event create(String memberId, EventCreateRequest request);

    Event update(String memberId, String profileId, EventUpdateRequest request);

    Boolean checkExistingCode(String code);

    Event codeJoin(String memberId, String code, ProfileCreateRequest request);

    Event join(String memberId, String eventId, ProfileCreateRequest request);

    Profile getMyProfile(String memberId, String eventId);

    void secession(String memberId, String eventId);

    void release(String memberId, ProfileReleaseRequest request);

    void delete(String memberId, String eventId);
}
