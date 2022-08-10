package com.github.dankook_univ.meetwork.board.application;

import com.github.dankook_univ.meetwork.board.domain.Board;
import com.github.dankook_univ.meetwork.board.infra.http.request.BoardCreateRequest;
import com.github.dankook_univ.meetwork.board.infra.http.request.BoardUpdateRequest;
import java.util.List;

public interface BoardService {

    Board create(String name, BoardCreateRequest request);

    Board update(String memberId, String boardId, BoardUpdateRequest request);

    Board get(String boardId);

    List<Board> getList(String memberId, String eventId);

    void delete(String memberId, String boardId);
}
