package com.github.dankook_univ.meetwork.profile.infra.persistence;

import com.github.dankook_univ.meetwork.profile.domain.Profile;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfileJpaRepository extends JpaRepository<Profile, UUID> {

    Optional<Profile> findByMemberIdAndEventId(UUID memberId, UUID eventId);

    Optional<Profile> findByEventIdAndNickname(UUID eventId, String nickname);

    void deleteByMemberIdAndEventId(UUID memberId, UUID eventId);

    Page<Profile> findByMemberId(UUID memberId, Pageable pageable);

    Page<Profile> findByEventId(UUID eventId, Pageable pageable);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Profile p where p.event.id = :eventId")
    void deleteAllByEventId(@Param("eventId") UUID eventId);
}
