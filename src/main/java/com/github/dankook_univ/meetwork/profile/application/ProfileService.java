package com.github.dankook_univ.meetwork.profile.application;

import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileUpdateRequest;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ProfileService {

    Profile get(String memberId, String eventId);

    Profile getById(String profileId);

    Profile create(String memberId, Event event, ProfileCreateRequest request, Boolean isAdmin);

    Profile update(String memberId, ProfileUpdateRequest request);

    void delete(String memberId, String eventId);

    void delete(Profile profile);

    void deleteByEventId(String eventId);

    List<Profile> getListByMemberId(String memberId, Pageable pageable);

    List<Profile> getListByEventIdAndAdminOnly(String eventId, Boolean adminOnly,
        Pageable pageable);

    Boolean isEventMember(String memberId, String eventId);
}
