package com.github.dankook_univ.meetwork.event.infra.http.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UpdateAdminRequest {

    @NotBlank
    String profileId;

    @NotBlank
    String eventId;

    @NotNull
    Boolean isAdmin;
}
