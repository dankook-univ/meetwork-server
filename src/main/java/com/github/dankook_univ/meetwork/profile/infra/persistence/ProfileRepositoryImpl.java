package com.github.dankook_univ.meetwork.profile.infra.persistence;

import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.domain.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProfileRepositoryImpl implements ProfileRepository {

	private final ProfileJpaRepository profileRepository;

	@Override
	public Optional<Profile> getById(String profileId) {
		return profileRepository.findById(UUID.fromString(profileId));
	}

	@Override
	public Optional<Profile> getByMemberIdAndEventId(String memberId, String eventId) {
		return profileRepository.findByMemberIdAndEventId(UUID.fromString(memberId), UUID.fromString(eventId));
	}

	@Override
	public Optional<Profile> getByEventIdAndNickname(String eventId, String nickname) {
		return profileRepository.findByEventIdAndNickname(UUID.fromString(eventId), nickname);
	}

	@Override
	public Profile save(Profile profile) {
		return profileRepository.save(profile);
	}

	@Override
	public void delete(String memberId, String eventId) {
		profileRepository.deleteByMemberIdAndEventId(UUID.fromString(memberId), UUID.fromString(eventId));
	}
}

