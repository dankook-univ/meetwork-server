package com.github.dankook_univ.meetwork.auth.infra.http.request;

import com.github.dankook_univ.meetwork.auth.domain.auth.AuthType;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInRequest {

    @NotBlank
    private AuthType type;

    @NotBlank
    private String token;

    @Builder
    public SignInRequest(AuthType type, String token) {
        this.type = type;
        this.token = token;
    }
}
