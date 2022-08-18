package com.github.dankook_univ.meetwork.chat.infra.persistence.participant;

import com.github.dankook_univ.meetwork.chat.domain.participant.ChatParticipant;
import com.github.dankook_univ.meetwork.chat.domain.participant.ChatParticipantRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatParticipantRepositoryImpl implements ChatParticipantRepository {

    private final ChatParticipantJpaRepository chatParticipantJpaRepository;

    @Override
    public List<ChatParticipant> getByRoomId(String roomId) {
        return chatParticipantJpaRepository.findByRoomId(UUID.fromString(roomId));
    }

    @Override
    public List<ChatParticipant> getByParticipantId(String participantId) {
        return chatParticipantJpaRepository.findByMemberId(UUID.fromString(participantId));
    }

    @Override
    public Optional<ChatParticipant> getByParticipantIdAndRoomId(
        String participantId, String roomId
    ) {
        return chatParticipantJpaRepository.findByMemberIdAndRoomId(
            UUID.fromString(participantId),
            UUID.fromString(roomId)
        );
    }

    @Override
    public ChatParticipant create(ChatParticipant participant) {
        return chatParticipantJpaRepository.save(participant);
    }
}
