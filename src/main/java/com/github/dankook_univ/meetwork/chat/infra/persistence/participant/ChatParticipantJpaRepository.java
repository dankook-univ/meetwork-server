package com.github.dankook_univ.meetwork.chat.infra.persistence.participant;

import com.github.dankook_univ.meetwork.chat.domain.participant.ChatParticipant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatParticipantJpaRepository extends JpaRepository<ChatParticipant, Long> {

    List<ChatParticipant> findByRoomId(Long roomId);

    List<ChatParticipant> findByMemberId(Long participantId);

    Optional<ChatParticipant> findByMemberIdAndRoomId(Long participantId, Long roomId);

    void deleteAllByMemberId(Long memberId);
}
