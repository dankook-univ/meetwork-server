package com.github.dankook_univ.meetwork.chat.infra.persistence.room;

import com.github.dankook_univ.meetwork.chat.domain.room.ChatRoom;
import com.github.dankook_univ.meetwork.chat.domain.room.ChatRoomRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepository {

    private final ChatRoomJpaRepository chatRoomJpaRepository;

    @Override
    public List<ChatRoom> getAll(String eventId) {
        return chatRoomJpaRepository.findByEventId(UUID.fromString(eventId));
    }

    @Override
    public ChatRoom save(ChatRoom room) {
        return chatRoomJpaRepository.save(room);
    }
}
