package com.github.dankook_univ.meetwork.chat.infra.socket.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageRequest {

    String roomId;
    String message;
}
