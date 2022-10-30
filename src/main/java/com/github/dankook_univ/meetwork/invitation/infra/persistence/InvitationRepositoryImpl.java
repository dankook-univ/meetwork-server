package com.github.dankook_univ.meetwork.invitation.infra.persistence;

import com.github.dankook_univ.meetwork.invitation.domain.Invitation;
import com.github.dankook_univ.meetwork.invitation.domain.InvitationRepository;
import java.util.List;
import java.util.Optional;
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
    public List<Invitation> getList(Long guestId) {
        return invitationRepository.getByGuestIdOrderByUpdatedAtDesc(guestId);
    }

    @Override
    public Optional<Invitation> getByGuestIdAndEventId(Long guestId, Long eventId) {
        return invitationRepository.getByGuestIdAndEventId(
            guestId,
            eventId
        );
    }

    @Override
    public void delete(Invitation invitation) {
        invitationRepository.delete(invitation);
    }

    @Override
    public void deleteByEventId(Long eventId) {
        invitationRepository.deleteAllByEventId(eventId);
    }
}
