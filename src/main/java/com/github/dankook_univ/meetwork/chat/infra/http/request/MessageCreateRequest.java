package com.github.dankook_univ.meetwork.chat.infra.http.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MessageCreateRequest {

    @NotBlank
    String message;
}
