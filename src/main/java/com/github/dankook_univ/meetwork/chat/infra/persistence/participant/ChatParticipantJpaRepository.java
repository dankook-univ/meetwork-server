package com.github.dankook_univ.meetwork.chat.infra.persistence.participant;

import com.github.dankook_univ.meetwork.chat.domain.participant.ChatParticipant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatParticipantJpaRepository extends JpaRepository<ChatParticipant, UUID> {

    List<ChatParticipant> findByMemberId(UUID participantId);

    Optional<ChatParticipant> findByMemberIdAndRoomId(UUID participantId, UUID roomId);
}
