package com.github.dankook_univ.meetwork.board.infra.http;

import com.github.dankook_univ.meetwork.board.application.BoardServiceImpl;
import com.github.dankook_univ.meetwork.board.domain.Board;
import com.github.dankook_univ.meetwork.board.infra.http.request.BoardCreateRequest;
import com.github.dankook_univ.meetwork.board.infra.http.request.BoardUpdateRequest;
import com.github.dankook_univ.meetwork.board.infra.http.response.BoardResponse;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardServiceImpl boardService;

    @PostMapping("/new")
    public ResponseEntity<BoardResponse> create(
        @ApiIgnore Authentication authentication,
        @RequestBody @Valid BoardCreateRequest request
    ) {
        return ResponseEntity.ok().body(
            boardService.create(Long.getLong(authentication.getName()), request).toResponse()
        );
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardResponse> update(
        @ApiIgnore Authentication authentication,
        @PathVariable("boardId") @Valid @NotBlank Long boardId,
        @RequestBody @Valid BoardUpdateRequest request
    ) {
        return ResponseEntity.ok().body(
            boardService.update(Long.getLong(authentication.getName()), boardId, request)
                .toResponse()
        );
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<List<BoardResponse>> getList(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @Valid @NotBlank Long eventId
    ) {
        return ResponseEntity.ok().body(
            boardService.getList(Long.getLong(authentication.getName()), eventId)
                .stream()
                .map(Board::toResponse)
                .collect(Collectors.toList())
        );
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Boolean> delete(
        @ApiIgnore Authentication authentication,
        @PathVariable("boardId") @Valid @NotBlank Long boardId
    ) {
        boardService.delete(Long.getLong(authentication.getName()), boardId);
        return ResponseEntity.ok().body(true);
    }

}
