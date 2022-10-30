package com.github.dankook_univ.meetwork.chat.domain.participant;

import java.util.List;
import java.util.Optional;

public interface ChatParticipantRepository {

    List<ChatParticipant> getByRoomId(Long roomId);

    List<ChatParticipant> getByParticipantId(Long participantId);

    Optional<ChatParticipant> getByParticipantIdAndRoomId(Long participantId, Long roomId);

    ChatParticipant create(ChatParticipant participant);

    void deleteByMemberId(Long memberId);
}
