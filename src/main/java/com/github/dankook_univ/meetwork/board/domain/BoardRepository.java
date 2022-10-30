package com.github.dankook_univ.meetwork.board.domain;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    Optional<Board> getById(Long id);

    Optional<Board> getByEventIdAndName(Long eventId, String name);

    Board save(Board board);

    List<Board> getListByEventId(Long eventId);

    void delete(Long boardId);

    void deleteByEventId(Long eventId);
}
