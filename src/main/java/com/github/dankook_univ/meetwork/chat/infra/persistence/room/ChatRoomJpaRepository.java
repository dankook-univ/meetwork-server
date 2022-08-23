package com.github.dankook_univ.meetwork.chat.infra.persistence.room;

import com.github.dankook_univ.meetwork.chat.domain.room.ChatRoom;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoom, UUID> {

    Optional<ChatRoom> findByEventIdAndName(UUID eventId, String name);

    List<ChatRoom> findByEventId(UUID eventId);

    void deleteById(@Nonnull UUID id);
    
    void deleteAllByEventId(UUID eventId);
}
