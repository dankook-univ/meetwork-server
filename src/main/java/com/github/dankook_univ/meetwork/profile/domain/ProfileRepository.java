package com.github.dankook_univ.meetwork.profile.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface ProfileRepository {

    Optional<Profile> getById(Long profileId);

    Optional<Profile> getByMemberIdAndEventId(Long memberId, Long eventId);

    Optional<Profile> getByEventIdAndNickname(Long eventId, String nickname);

    List<Profile> getByMemberId(Long memberId, Pageable pageable);

    List<Profile> getByEventIdAndAdminOnly(Long eventId, Boolean adminOnly, Pageable pageable);

    Profile save(Profile profile);

    void delete(Long memberId, Long eventId);

    void delete(Profile profile);

    void deleteAllByEventId(Long eventId);
}
