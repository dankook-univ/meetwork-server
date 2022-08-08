package com.github.dankook_univ.meetwork.board.infra.http.response;

import com.github.dankook_univ.meetwork.event.infra.http.response.EventResponse;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class BoardResponse {

    UUID id;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    EventResponse event;

    String name;

    Boolean adminOnly;
}
