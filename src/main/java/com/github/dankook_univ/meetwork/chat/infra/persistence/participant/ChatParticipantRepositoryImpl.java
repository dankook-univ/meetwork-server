package com.github.dankook_univ.meetwork.chat.infra.persistence.participant;

import com.github.dankook_univ.meetwork.chat.domain.participant.ChatParticipant;
import com.github.dankook_univ.meetwork.chat.domain.participant.ChatParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatParticipantRepositoryImpl implements ChatParticipantRepository {

    private final ChatParticipantJpaRepository chatParticipantJpaRepository;

    @Override
    public ChatParticipant create(ChatParticipant participant) {
        return chatParticipantJpaRepository.save(participant);
    }
}
