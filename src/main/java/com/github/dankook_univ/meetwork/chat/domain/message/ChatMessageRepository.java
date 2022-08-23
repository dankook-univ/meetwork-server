package com.github.dankook_univ.meetwork.chat.domain.message;

import java.util.List;

public interface ChatMessageRepository {

    List<ChatMessage> getByRoomId(String roomId);

    ChatMessage save(ChatMessage message);

    void deleteBySenderId(String senderId);
}
