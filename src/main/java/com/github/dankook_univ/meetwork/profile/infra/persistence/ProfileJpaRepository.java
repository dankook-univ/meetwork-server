package com.github.dankook_univ.meetwork.profile.infra.persistence;

import com.github.dankook_univ.meetwork.profile.domain.Profile;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileJpaRepository extends JpaRepository<Profile, UUID> {

    Optional<Profile> getByMemberIdAndEventId(UUID memberId, UUID eventId);

    Optional<Profile> findByEventIdAndNickname(UUID eventId, String nickname);

    void deleteByMemberIdAndEventId(UUID memberId, UUID eventId);
}
