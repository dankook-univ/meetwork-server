package com.github.dankook_univ.meetwork.chat.domain.room;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository {

    List<ChatRoom> getAll(Long eventId);

    Optional<ChatRoom> getById(Long roomId);

    Optional<ChatRoom> getByEventIdAndName(Long eventId, String name);

    ChatRoom save(ChatRoom room);

    void deleteById(Long roomId);

    void deleteByEventId(Long eventId);

    void deleteByOrganizerId(Long organizerId);
}
