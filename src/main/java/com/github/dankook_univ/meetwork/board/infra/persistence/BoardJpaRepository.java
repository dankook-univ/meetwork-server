package com.github.dankook_univ.meetwork.board.infra.persistence;

import com.github.dankook_univ.meetwork.board.domain.Board;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepository extends JpaRepository<Board, Long> {

    Optional<Board> getByEventIdAndName(Long eventId, String name);

    List<Board> findByEventIdOrderByUpdatedAtAsc(Long eventId);

    void deleteAllByEventId(Long eventId);
}
