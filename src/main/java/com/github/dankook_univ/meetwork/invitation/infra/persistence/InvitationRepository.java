package com.github.dankook_univ.meetwork.invitation.infra.persistence;

import com.github.dankook_univ.meetwork.invitation.domain.Invitation;
import java.util.List;
import java.util.Optional;

public interface InvitationRepository {

    Invitation save(Invitation invitation);

    List<Invitation> getList(String guestId);

    Optional<Invitation> getByGuestIdAndEventId(String guestId, String eventId);

    void delete(Invitation invitation);

}
