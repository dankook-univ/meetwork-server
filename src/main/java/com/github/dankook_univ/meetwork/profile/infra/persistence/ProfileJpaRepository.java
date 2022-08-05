package com.github.dankook_univ.meetwork.profile.infra.persistence;

import com.github.dankook_univ.meetwork.profile.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileJpaRepository extends JpaRepository<Profile, UUID> {
	Optional<Profile> findByMemberIdAndEventId(UUID memberId, UUID eventId);

	Optional<Profile> findByEventIdAndNickname(UUID eventId, String nickname);

	void deleteByMemberIdAndEventId(UUID memberId, UUID eventId);
}
