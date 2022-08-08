package com.github.dankook_univ.meetwork.profile.domain;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository {

    Optional<Profile> getById(String profileId);

    Optional<Profile> getByMemberIdAndEventId(String memberId, String eventId);

    Optional<Profile> getByEventIdAndNickname(String eventId, String nickname);

    List<Profile> getByMemberId(String memberId);

    List<Profile> getByEventId(String eventId);

    Profile save(Profile profile);

    void delete(String memberId, String eventId);
}
