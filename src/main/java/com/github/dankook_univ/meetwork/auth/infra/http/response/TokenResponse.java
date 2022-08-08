package com.github.dankook_univ.meetwork.auth.infra.http.response;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TokenResponse {

    String accessToken;

    String refreshToken;

    LocalDateTime accessTokenExpirationDate;

    LocalDateTime refreshTokenExpirationDate;
}
