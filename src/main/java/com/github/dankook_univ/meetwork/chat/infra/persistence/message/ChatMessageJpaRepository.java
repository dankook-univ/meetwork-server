package com.github.dankook_univ.meetwork.chat.infra.persistence.message;

import com.github.dankook_univ.meetwork.chat.domain.message.ChatMessage;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageJpaRepository extends JpaRepository<ChatMessage, UUID> {

    List<ChatMessage> findByRoomIdOrderByCreatedAtDesc(UUID roomId);
}
