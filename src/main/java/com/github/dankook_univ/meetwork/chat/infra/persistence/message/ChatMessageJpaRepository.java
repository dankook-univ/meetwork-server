package com.github.dankook_univ.meetwork.chat.infra.persistence.message;

import com.github.dankook_univ.meetwork.chat.domain.message.ChatMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageJpaRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByRoomIdOrderByCreatedAtDesc(Long roomId);

    void deleteAllBySenderId(Long senderId);
}
