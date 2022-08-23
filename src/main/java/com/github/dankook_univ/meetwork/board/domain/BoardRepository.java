package com.github.dankook_univ.meetwork.board.domain;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    Optional<Board> getById(String id);

    Optional<Board> getByEventIdAndName(String eventId, String name);

    Board save(Board board);

    List<Board> getListByEventId(String eventId);

    void delete(String boardId);

    void deleteByEventId(String eventId);
}
