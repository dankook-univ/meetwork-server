package com.github.dankook_univ.meetwork.chat.infra.persistence.room;

import com.github.dankook_univ.meetwork.chat.domain.room.ChatRoom;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByEventIdAndName(Long eventId, String name);

    List<ChatRoom> findByEventId(Long eventId);

    void deleteById(@Nonnull Long id);

    void deleteAllByEventId(Long eventId);

    void deleteAllByOrganizerId(Long organizerId);
}
