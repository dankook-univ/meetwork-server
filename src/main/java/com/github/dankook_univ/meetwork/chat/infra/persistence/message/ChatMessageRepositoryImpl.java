package com.github.dankook_univ.meetwork.chat.infra.persistence.message;

import com.github.dankook_univ.meetwork.chat.domain.message.ChatMessage;
import com.github.dankook_univ.meetwork.chat.domain.message.ChatMessageRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepository {

    private final ChatMessageJpaRepository chatMessageJpaRepository;

    @Override
    public List<ChatMessage> getByRoomId(String roomId) {
        return chatMessageJpaRepository.findByRoomId(UUID.fromString(roomId));
    }

    @Override
    public ChatMessage save(ChatMessage message) {
        return chatMessageJpaRepository.save(message);
    }
}
