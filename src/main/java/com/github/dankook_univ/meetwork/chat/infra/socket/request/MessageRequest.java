package com.github.dankook_univ.meetwork.chat.infra.socket.request;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageRequest {

    @NotBlank
    String roomId;

    @NotBlank
    String message;
}
