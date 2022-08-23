package com.github.dankook_univ.meetwork.chat.domain.room;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository {

    List<ChatRoom> getAll(String eventId);

    Optional<ChatRoom> getById(String roomId);

    Optional<ChatRoom> getByEventIdAndName(String EventId, String name);

    ChatRoom save(ChatRoom room);

    void deleteById(String roomId);

    void deleteByEventId(String eventId);
}
