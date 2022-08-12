package com.github.dankook_univ.meetwork.chat.infra.http.request;

import java.util.ArrayList;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatRoomCreateRequest {

    @NotEmpty
    @NotNull
    String eventId;

    @NotEmpty
    @NotNull
    String name;

    @NotNull
    Boolean isPrivate;

    ArrayList<String> participantIds;
}
