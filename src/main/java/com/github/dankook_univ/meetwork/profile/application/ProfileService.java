package com.github.dankook_univ.meetwork.profile.application;

import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileUpdateRequest;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ProfileService {

    Profile get(Long memberId, Long eventId);

    Profile getById(Long profileId);

    Profile create(Long memberId, Event event, ProfileCreateRequest request, Boolean isAdmin);

    Profile update(Long memberId, ProfileUpdateRequest request);

    void delete(Long memberId, Long eventId);

    void delete(Profile profile);

    void deleteByEventId(Long eventId);

    List<Profile> getListByMemberId(Long memberId, Pageable pageable);

    List<Profile> getListByEventIdAndAdminOnly(Long eventId, Boolean adminOnly,
        Pageable pageable);

    Boolean isEventMember(Long memberId, Long eventId);
}
