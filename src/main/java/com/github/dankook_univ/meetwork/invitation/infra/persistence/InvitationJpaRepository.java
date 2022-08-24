package com.github.dankook_univ.meetwork.invitation.infra.persistence;

import com.github.dankook_univ.meetwork.invitation.domain.Invitation;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvitationJpaRepository extends JpaRepository<Invitation, UUID> {

    List<Invitation> getByGuestIdOrderByUpdatedAtDesc(UUID guestId);

    Optional<Invitation> getByGuestIdAndEventId(UUID guestId, UUID eventId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Invitation i where i.event.id = :eventId")
    void deleteAllByEventId(@Param("eventId") UUID eventId);
}
