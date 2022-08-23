package com.github.dankook_univ.meetwork.profile.infra.persistence;

import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.domain.ProfileRepository;
import com.github.dankook_univ.meetwork.profile.domain.QProfile;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProfileRepositoryImpl implements ProfileRepository {

    private final ProfileJpaRepository profileRepository;

    private final JPAQueryFactory queryFactory;
    private final QProfile profile = QProfile.profile;

    @Override
    public Optional<Profile> getById(String profileId) {
        return profileRepository.findById(UUID.fromString(profileId));
    }

    @Override
    public Optional<Profile> getByMemberIdAndEventId(String memberId, String eventId) {
        return profileRepository.findByMemberIdAndEventId(UUID.fromString(memberId),
            UUID.fromString(eventId));
    }

    @Override
    public Optional<Profile> getByEventIdAndNickname(String eventId, String nickname) {
        return profileRepository.findByEventIdAndNickname(UUID.fromString(eventId), nickname);
    }

    @Override
    public List<Profile> getByMemberId(String memberId, Pageable pageable) {
        return profileRepository.findByMemberId(UUID.fromString(memberId), pageable).getContent();
    }

    @Override
    public List<Profile> getByEventIdAndAdminOnly(
        String eventId,
        Boolean adminOnly,
        Pageable pageable
    ) {
        return queryFactory
            .selectFrom(profile)
            .where(
                profile.event.id.eq(UUID.fromString(eventId)),
                adminOnlyEq(adminOnly)
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    @Override
    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public void delete(String memberId, String eventId) {
        profileRepository.deleteByMemberIdAndEventId(UUID.fromString(memberId),
            UUID.fromString(eventId));
    }

    @Override
    public void delete(Profile profile) {
        profileRepository.delete(profile);
    }

    @Override
    public void deleteAllByEventId(String eventId) {
        profileRepository.deleteAllByEventId(UUID.fromString(eventId));
    }

    private BooleanExpression adminOnlyEq(Boolean adminOnly) {
        return adminOnly != null ? profile.isAdmin.eq(adminOnly) : null;
    }
}

