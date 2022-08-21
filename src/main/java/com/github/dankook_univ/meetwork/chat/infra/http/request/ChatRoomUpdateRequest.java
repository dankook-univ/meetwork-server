package com.github.dankook_univ.meetwork.chat.infra.http.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatRoomUpdateRequest {

    String name;
    Boolean isPrivate;
}
