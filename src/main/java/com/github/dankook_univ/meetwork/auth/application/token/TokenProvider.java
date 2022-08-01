package com.github.dankook_univ.meetwork.auth.application.token;

import com.github.dankook_univ.meetwork.auth.domain.auth.Auth;
import com.github.dankook_univ.meetwork.auth.domain.token.Token;
import org.springframework.security.core.Authentication;

public interface TokenProvider {

    Token create(Auth auth);

    boolean remove(Auth auth);

    boolean validation(String token);

    Auth parse(String token);

    Authentication getAuthentication(String accessToken);
}
