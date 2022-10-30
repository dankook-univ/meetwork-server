package com.github.dankook_univ.meetwork.profile.infra.persistence;

import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.domain.ProfileRepository;
import com.github.dankook_univ.meetwork.profile.domain.QProfile;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
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
    public Optional<Profile> getById(Long profileId) {
        return profileRepository.findById(profileId);
    }

    @Override
    public Optional<Profile> getByMemberIdAndEventId(Long memberId, Long eventId) {
        return profileRepository.findByMemberIdAndEventId(memberId, eventId);
    }

    @Override
    public Optional<Profile> getByEventIdAndNickname(Long eventId, String nickname) {
        return profileRepository.findByEventIdAndNickname(eventId, nickname);
    }

    @Override
    public List<Profile> getByMemberId(Long memberId, Pageable pageable) {
        return profileRepository.findByMemberId(memberId, pageable).getContent();
    }

    @Override
    public List<Profile> getByEventIdAndAdminOnly(
        Long eventId,
        Boolean adminOnly,
        Pageable pageable
    ) {
        return queryFactory
            .selectFrom(profile)
            .where(profile.event.id.eq(eventId), adminOnlyEq(adminOnly))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    @Override
    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public void delete(Long memberId, Long eventId) {
        profileRepository.deleteByMemberIdAndEventId(memberId, eventId);
    }

    @Override
    public void delete(Profile profile) {
        profileRepository.delete(profile);
    }

    @Override
    public void deleteAllByEventId(Long eventId) {
        profileRepository.deleteAllByEventId(eventId);
    }

    private BooleanExpression adminOnlyEq(Boolean adminOnly) {
        return adminOnly != null ? profile.isAdmin.eq(adminOnly) : null;
    }
}

