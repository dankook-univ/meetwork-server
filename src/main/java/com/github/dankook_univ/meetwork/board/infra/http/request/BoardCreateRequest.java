package com.github.dankook_univ.meetwork.board.infra.http.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardCreateRequest {

    @NotBlank
    Long eventId;

    @NotBlank
    String name;

    @NotNull
    Boolean adminOnly;
}
