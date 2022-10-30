package com.github.dankook_univ.meetwork.invitation.domain;

import java.util.List;
import java.util.Optional;

public interface InvitationRepository {

    Invitation save(Invitation invitation);

    List<Invitation> getList(Long guestId);

    Optional<Invitation> getByGuestIdAndEventId(Long guestId, Long eventId);

    void delete(Invitation invitation);

    void deleteByEventId(Long eventId);
}
