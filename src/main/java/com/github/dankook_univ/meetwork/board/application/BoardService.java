package com.github.dankook_univ.meetwork.board.application;

import com.github.dankook_univ.meetwork.board.domain.Board;
import com.github.dankook_univ.meetwork.board.infra.http.request.BoardCreateRequest;
import com.github.dankook_univ.meetwork.board.infra.http.request.BoardUpdateRequest;
import java.util.List;

public interface BoardService {

    Board create(Long memberId, BoardCreateRequest request);

    Board update(Long memberId, Long boardId, BoardUpdateRequest request);

    Board get(Long boardId);

    List<Board> getList(Long memberId, Long eventId);

    void delete(Long memberId, Long boardId);

    void deleteByEventId(Long eventId);
}
