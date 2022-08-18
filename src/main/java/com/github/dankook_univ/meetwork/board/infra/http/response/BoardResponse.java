package com.github.dankook_univ.meetwork.board.infra.http.response;

import com.github.dankook_univ.meetwork.board.domain.Board;
import com.github.dankook_univ.meetwork.event.infra.http.response.EventResponse;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardResponse {

    String id;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    EventResponse event;

    String name;

    Boolean adminOnly;

    @Builder
    public BoardResponse(
        Board board
    ) {
        this.id = board.getId().toString();
        this.name = board.getName();
        this.event = board.getEvent().toResponse();
        this.adminOnly = board.getAdminOnly();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
    }
}
