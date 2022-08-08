package com.github.dankook_univ.meetwork.auth.application.auth;

import com.github.dankook_univ.meetwork.auth.infra.http.request.ReissueRequest;
import com.github.dankook_univ.meetwork.auth.infra.http.request.SignInRequest;
import com.github.dankook_univ.meetwork.auth.infra.http.request.SignUpRequest;
import com.github.dankook_univ.meetwork.auth.infra.http.response.TokenResponse;

public interface AuthService {

    TokenResponse signIn(SignInRequest request);

    TokenResponse signUp(SignUpRequest request);

    TokenResponse reissue(ReissueRequest request);

    boolean signOut(String memberId);
}
