package com.github.dankook_univ.meetwork.chat.infra.persistence.room;

import com.github.dankook_univ.meetwork.chat.domain.room.ChatRoom;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoom, UUID> {

    List<ChatRoom> findByEventId(UUID eventId);
}
