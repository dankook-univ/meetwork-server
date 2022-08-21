package com.github.dankook_univ.meetwork.event.infra.http.request;

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

    String profileId;

    String eventId;

    Boolean isAdmin;
}
