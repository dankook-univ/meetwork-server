package com.github.dankook_univ.meetwork.board.infra.persistence;

import com.github.dankook_univ.meetwork.board.domain.Board;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepository extends JpaRepository<Board, UUID> {

    Optional<Board> getByEventIdAndName(UUID eventId, String name);

    List<Board> findByEventIdOrderByUpdatedAtAsc(UUID eventId);

    void deleteAllByEventId(UUID eventId);
}
