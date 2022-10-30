package com.github.dankook_univ.meetwork.board.infra.persistence;

import com.github.dankook_univ.meetwork.board.domain.Board;
import com.github.dankook_univ.meetwork.board.domain.BoardRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

    private final BoardJpaRepository boardRepository;

    @Override
    public Optional<Board> getById(Long id) {
        return boardRepository.findById(id);
    }

    @Override
    public Optional<Board> getByEventIdAndName(Long eventId, String name) {
        return boardRepository.getByEventIdAndName(eventId, name);
    }

    @Override
    public Board save(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public List<Board> getListByEventId(Long eventId) {
        return boardRepository.findByEventIdOrderByUpdatedAtAsc(eventId);
    }

    @Override
    public void delete(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    @Override
    public void deleteByEventId(Long eventId) {
        boardRepository.deleteAllByEventId(eventId);
    }
}
