package com.github.dankook_univ.meetwork.auth.application.auth;

import com.github.dankook_univ.meetwork.auth.domain.token.Token;
import com.github.dankook_univ.meetwork.auth.infra.http.request.ReissueRequest;
import com.github.dankook_univ.meetwork.auth.infra.http.request.SignInRequest;
import com.github.dankook_univ.meetwork.auth.infra.http.request.SignUpRequest;

public interface AuthService {

    Token signIn(SignInRequest request);

    Token signUp(SignUpRequest request);

    Token reissue(ReissueRequest request);

    boolean signOut(String memberId);
}
