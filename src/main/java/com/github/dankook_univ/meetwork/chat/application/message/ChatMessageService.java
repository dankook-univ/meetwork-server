package com.github.dankook_univ.meetwork.chat.application.message;

import com.github.dankook_univ.meetwork.chat.domain.message.ChatMessage;
import com.github.dankook_univ.meetwork.chat.exceptions.NotParticipatedMemberException;
import java.util.List;

public interface ChatMessageService {

    List<ChatMessage> getByRoomId(Long memberId, Long roomId)
        throws NotParticipatedMemberException;

    ChatMessage send(Long memberId, Long roomId, String message)
        throws NotParticipatedMemberException;
}
