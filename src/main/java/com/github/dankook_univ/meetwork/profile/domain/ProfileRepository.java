package com.github.dankook_univ.meetwork.profile.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface ProfileRepository {

    Optional<Profile> getById(String profileId);

    Optional<Profile> getByMemberIdAndEventId(String memberId, String eventId);

    Optional<Profile> getByEventIdAndNickname(String eventId, String nickname);

    List<Profile> getByMemberId(String memberId, Pageable pageable);

    List<Profile> getByEventId(String eventId, Pageable pageable);

    Profile save(Profile profile);

    void delete(String memberId, String eventId);

    void deleteAllByEventId(String eventId);
}
