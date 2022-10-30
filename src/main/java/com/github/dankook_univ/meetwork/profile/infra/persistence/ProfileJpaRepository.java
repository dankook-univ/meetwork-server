package com.github.dankook_univ.meetwork.profile.infra.persistence;

import com.github.dankook_univ.meetwork.profile.domain.Profile;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfileJpaRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByMemberIdAndEventId(Long memberId, Long eventId);

    Optional<Profile> findByEventIdAndNickname(Long eventId, String nickname);

    void deleteByMemberIdAndEventId(Long memberId, Long eventId);

    Page<Profile> findByMemberId(Long memberId, Pageable pageable);

    Page<Profile> findByEventId(Long eventId, Pageable pageable);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Profile p where p.event.id = :eventId")
    void deleteAllByEventId(@Param("eventId") Long eventId);
}
