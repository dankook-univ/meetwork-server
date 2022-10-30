package com.github.dankook_univ.meetwork.chat.infra.persistence.room;

import com.github.dankook_univ.meetwork.chat.domain.room.ChatRoom;
import com.github.dankook_univ.meetwork.chat.domain.room.ChatRoomRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepository {

    private final ChatRoomJpaRepository chatRoomJpaRepository;

    @Override
    public List<ChatRoom> getAll(Long eventId) {
        return chatRoomJpaRepository.findByEventId(eventId);
    }

    @Override
    public Optional<ChatRoom> getById(Long roomId) {
        return chatRoomJpaRepository.findById(roomId);
    }

    @Override
    public Optional<ChatRoom> getByEventIdAndName(Long eventId, String name) {
        return chatRoomJpaRepository.findByEventIdAndName(eventId, name);
    }

    @Override
    public ChatRoom save(ChatRoom room) {
        return chatRoomJpaRepository.save(room);
    }

    @Override
    public void deleteById(Long roomId) {
        chatRoomJpaRepository.deleteById(roomId);
    }

    @Override
    public void deleteByEventId(Long eventId) {
        chatRoomJpaRepository.deleteAllByEventId(eventId);
    }

    @Override
    public void deleteByOrganizerId(Long organizerId) {
        chatRoomJpaRepository.deleteAllByOrganizerId(organizerId);
    }
}
