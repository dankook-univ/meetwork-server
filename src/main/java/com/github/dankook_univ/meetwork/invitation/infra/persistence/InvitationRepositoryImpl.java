package com.github.dankook_univ.meetwork.invitation.infra.persistence;

import com.github.dankook_univ.meetwork.invitation.domain.Invitation;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InvitationRepositoryImpl implements InvitationRepository {

    private final InvitationJpaRepository invitationRepository;

    @Override
    public Invitation save(Invitation invitation) {
        return invitationRepository.save(invitation);
    }

    @Override
    public List<Invitation> getList(String guestId) {
        return invitationRepository.getByGuestIdOrderByUpdatedAtDesc(UUID.fromString(guestId));
    }

    @Override
    public Optional<Invitation> getByGuestIdAndEventId(String guestId, String eventId) {
        return invitationRepository.getByGuestIdAndEventId(
            UUID.fromString(guestId),
            UUID.fromString(eventId)
        );
    }

    @Override
    public void delete(Invitation invitation) {
        invitationRepository.delete(invitation);
    }

    @Override
    public void deleteByEventId(String eventId) {
        invitationRepository.deleteAllByEventId(UUID.fromString(eventId));
    }
}
