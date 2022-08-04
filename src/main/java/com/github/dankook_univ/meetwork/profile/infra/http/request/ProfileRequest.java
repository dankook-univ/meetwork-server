package com.github.dankook_univ.meetwork.profile.infra.http.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileRequest {

    @NotNull
    @NotEmpty
    String nickname;

    @NotEmpty
    String bio;

    @NotNull
    Boolean isAdmin;

    @Builder
    public ProfileRequest(
        String nickname, String bio, Boolean isAdmin
    ) {
        this.nickname = nickname;
        this.bio = bio;
        this.isAdmin = isAdmin;
    }
}
