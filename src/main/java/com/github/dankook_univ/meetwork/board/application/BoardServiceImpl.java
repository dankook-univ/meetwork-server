package com.github.dankook_univ.meetwork.board.application;

import com.github.dankook_univ.meetwork.board.domain.Board;
import com.github.dankook_univ.meetwork.board.exceptions.ExistingBoardNameException;
import com.github.dankook_univ.meetwork.board.exceptions.NotFoundBoardException;
import com.github.dankook_univ.meetwork.board.exceptions.NotFoundBoardPermissionException;
import com.github.dankook_univ.meetwork.board.infra.http.request.BoardCreateRequest;
import com.github.dankook_univ.meetwork.board.infra.http.request.BoardUpdateRequest;
import com.github.dankook_univ.meetwork.board.infra.persistence.BoardRepositoryImpl;
import com.github.dankook_univ.meetwork.common.service.SecurityUtilService;
import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.event.exceptions.NotFoundEventException;
import com.github.dankook_univ.meetwork.event.infra.persistence.EventRepositoryImpl;
import com.github.dankook_univ.meetwork.post.infra.persistence.PostRepositoryImpl;
import com.github.dankook_univ.meetwork.profile.application.ProfileServiceImpl;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final SecurityUtilService securityUtilService;
    private final BoardRepositoryImpl boardRepository;
    private final ProfileServiceImpl profileService;
    private final EventRepositoryImpl eventRepository;
    private final PostRepositoryImpl postRepository;

    @Override
    @Transactional
    public Board create(String memberId, BoardCreateRequest request) {
        if (!profileService.get(memberId, request.getEventId()).getIsAdmin()) {
            throw new NotFoundBoardPermissionException();
        }

        if (
            boardRepository.getByEventIdAndName(
                request.getEventId(),
                request.getName()
            ).isPresent()
        ) {
            throw new ExistingBoardNameException();
        }

        Event event = eventRepository.getById(request.getEventId())
            .orElseThrow(NotFoundEventException::new);

        return boardRepository.save(
            Board.builder()
                .event(event)
                .name(securityUtilService.protectInputValue(request.getName()))
                .adminOnly(request.getAdminOnly())
                .build()
        );
    }

    @Override
    @Transactional
    public Board update(String memberId, String boardId, BoardUpdateRequest request) {
        Board board = getById(boardId);
        if (!profileService.get(memberId, board.getEvent().getId().toString()).getIsAdmin()) {
            throw new NotFoundBoardPermissionException();
        }

        return board.update(
            securityUtilService.protectInputValue(request.getName()),
            request.getAdminOnly()
        );
    }

    @Override
    public Board get(String boardId) {
        return getById(boardId);
    }

    @Override
    public List<Board> getList(String memberId, String eventId) {
        if (!profileService.isEventMember(memberId, eventId)) {
            throw new NotFoundBoardPermissionException();
        }

        return boardRepository.getListByEventId(eventId);
    }

    @Override
    @Transactional
    public void delete(String memberId, String boardId) {
        Board board = getById(boardId);
        if (!profileService.get(memberId, board.getEvent().getId().toString()).getIsAdmin()) {
            throw new NotFoundBoardPermissionException();
        }

        boardRepository.delete(boardId);
    }

    @Override
    @Transactional
    public void deleteByEventId(String eventId) {
        List<Board> boards = boardRepository.getListByEventId(eventId);
        boards.forEach(board -> {
            postRepository.deleteByBoardId(board.getId().toString());
            boardRepository.delete(board.getId().toString());
        });
    }

    public Map<String, Boolean> automaticBoard() {
        Map<String, Boolean> boards = new LinkedHashMap<>();

        boards.put("공지 게시판", true);
        boards.put("QnA 게시판", false);
        boards.put("자유 게시판", false);

        return boards;
    }

    private Board getById(String boardId) {
        return boardRepository.getById(boardId).orElseThrow(NotFoundBoardException::new);
    }
}
