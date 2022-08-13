package com.github.dankook_univ.meetwork.chat.domain.participant;

import java.util.List;
import java.util.Optional;

public interface ChatParticipantRepository {

    List<ChatParticipant> getByRoomId(String roomId);

    List<ChatParticipant> getByParticipantId(String participantId);

    Optional<ChatParticipant> getByParticipantIdAndRoomId(String participantId, String roomId);

    ChatParticipant create(ChatParticipant participant);
}
