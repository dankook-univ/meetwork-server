package com.github.dankook_univ.meetwork.chat.infra.http.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MessageCreateRequest {

    @NotNull
    @NotEmpty
    String message;
}
