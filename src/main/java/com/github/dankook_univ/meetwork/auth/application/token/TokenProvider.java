package com.github.dankook_univ.meetwork.auth.application.token;

import com.github.dankook_univ.meetwork.auth.domain.auth.Auth;
import com.github.dankook_univ.meetwork.auth.infra.http.response.TokenResponse;
import org.springframework.security.core.Authentication;

public interface TokenProvider {

    TokenResponse create(Auth auth);

    boolean remove(Auth auth);

    boolean validation(String token);

    Auth parse(String token);

    Authentication getAuthentication(String accessToken);
}
