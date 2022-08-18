package com.github.dankook_univ.meetwork.chat.infra.http.request;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatRoomCreateRequest {

    @NotEmpty
    @NotNull
    String name;

    @NotNull
    Boolean isPrivate;

    List<String> participantIds;
}
