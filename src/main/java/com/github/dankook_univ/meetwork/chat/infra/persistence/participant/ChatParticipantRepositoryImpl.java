package com.github.dankook_univ.meetwork.chat.infra.persistence.participant;

import com.github.dankook_univ.meetwork.chat.domain.participant.ChatParticipant;
import com.github.dankook_univ.meetwork.chat.domain.participant.ChatParticipantRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatParticipantRepositoryImpl implements ChatParticipantRepository {

    private final ChatParticipantJpaRepository chatParticipantJpaRepository;

    @Override
    public List<ChatParticipant> getByRoomId(Long roomId) {
        return chatParticipantJpaRepository.findByRoomId(roomId);
    }

    @Override
    public List<ChatParticipant> getByParticipantId(Long participantId) {
        return chatParticipantJpaRepository.findByMemberId(participantId);
    }

    @Override
    public Optional<ChatParticipant> getByParticipantIdAndRoomId(
        Long participantId, Long roomId
    ) {
        return chatParticipantJpaRepository.findByMemberIdAndRoomId(
            participantId,
            roomId
        );
    }

    @Override
    public ChatParticipant create(ChatParticipant participant) {
        return chatParticipantJpaRepository.save(participant);
    }

    @Override
    public void deleteByMemberId(Long memberId) {
        chatParticipantJpaRepository.deleteAllByMemberId(memberId);
    }
}
