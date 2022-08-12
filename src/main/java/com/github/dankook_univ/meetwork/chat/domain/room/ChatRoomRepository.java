package com.github.dankook_univ.meetwork.chat.domain.room;

import java.util.List;

public interface ChatRoomRepository {

    List<ChatRoom> getAll(String eventId);

    ChatRoom save(ChatRoom room);
}
