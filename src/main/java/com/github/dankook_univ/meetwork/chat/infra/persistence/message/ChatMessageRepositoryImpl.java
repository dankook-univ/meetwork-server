package com.github.dankook_univ.meetwork.chat.infra.persistence.message;

import com.github.dankook_univ.meetwork.chat.domain.message.ChatMessage;
import com.github.dankook_univ.meetwork.chat.domain.message.ChatMessageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepository {

    private final ChatMessageJpaRepository chatMessageJpaRepository;

    @Override
    public List<ChatMessage> getByRoomId(Long roomId) {
        return chatMessageJpaRepository.findByRoomIdOrderByCreatedAtDesc(roomId);
    }

    @Override
    public ChatMessage save(ChatMessage message) {
        return chatMessageJpaRepository.save(message);
    }

    @Override
    public void deleteBySenderId(Long senderId) {
        chatMessageJpaRepository.deleteAllBySenderId(senderId);
    }
}
