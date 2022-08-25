package com.github.dankook_univ.meetwork.invitation.domain;

import java.util.List;
import java.util.Optional;

public interface InvitationRepository {

    Invitation save(Invitation invitation);

    List<Invitation> getList(String guestId);

    Optional<Invitation> getByGuestIdAndEventId(String guestId, String eventId);

    void delete(Invitation invitation);

    void deleteByEventId(String eventId);
}
