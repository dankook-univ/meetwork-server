package com.github.dankook_univ.meetwork.board.infra.persistence;

import com.github.dankook_univ.meetwork.board.domain.Board;
import com.github.dankook_univ.meetwork.board.domain.BoardRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

    private final BoardJpaRepository boardRepository;

    @Override
    public Optional<Board> getById(String id) {
        return boardRepository.findById(UUID.fromString(id));
    }

    @Override
    public Optional<Board> getByEventIdAndName(String eventId, String name) {
        return boardRepository.getByEventIdAndName(UUID.fromString(eventId), name);
    }

    @Override
    public Board save(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public List<Board> getListByEventId(String eventId) {
        return boardRepository.findByEventIdOrderByUpdatedAtAsc(UUID.fromString(eventId));
    }

    @Override
    public void delete(String boardId) {
        boardRepository.deleteById(UUID.fromString(boardId));
    }

    @Override
    public void deleteByEventId(String eventId) {
        boardRepository.deleteAllByEventId(UUID.fromString(eventId));
    }
}
