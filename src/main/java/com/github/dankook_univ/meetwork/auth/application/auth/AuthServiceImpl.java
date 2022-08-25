package com.github.dankook_univ.meetwork.auth.application.auth;

import com.github.dankook_univ.meetwork.auth.application.kakao.KakaoServiceImpl;
import com.github.dankook_univ.meetwork.auth.application.token.TokenProviderImpl;
import com.github.dankook_univ.meetwork.auth.domain.auth.Auth;
import com.github.dankook_univ.meetwork.auth.domain.auth.AuthType;
import com.github.dankook_univ.meetwork.auth.exceptions.ExistingAuthException;
import com.github.dankook_univ.meetwork.auth.exceptions.InvalidClientException;
import com.github.dankook_univ.meetwork.auth.exceptions.InvalidTokenException;
import com.github.dankook_univ.meetwork.auth.exceptions.NotFoundAuthException;
import com.github.dankook_univ.meetwork.auth.infra.http.request.ReissueRequest;
import com.github.dankook_univ.meetwork.auth.infra.http.request.SignInRequest;
import com.github.dankook_univ.meetwork.auth.infra.http.request.SignUpRequest;
import com.github.dankook_univ.meetwork.auth.infra.http.response.TokenResponse;
import com.github.dankook_univ.meetwork.auth.infra.persistence.AuthRepositoryImpl;
import com.github.dankook_univ.meetwork.common.service.SecurityUtilService;
import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.member.infra.persistence.MemberRepositoryImpl;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final SecurityUtilService securityUtilService;
    private final TokenProviderImpl tokenProvider;
    private final AuthRepositoryImpl authRepository;
    private final MemberRepositoryImpl memberRepository;
    private final KakaoServiceImpl kakaoService;

    @Override
    @Transactional
    public TokenResponse signIn(SignInRequest request) {
        Auth auth = authRepository.getByAuthTypeAndClientId(
            request.getType(),
            getClientId(request.getType(), request.getToken())
        ).orElseThrow(NotFoundAuthException::new);

        return tokenProvider.create(auth);
    }

    @Override
    @Transactional
    public TokenResponse signUp(SignUpRequest request) {
        String clientId = getClientId(request.getType(), request.getToken());
        if (authRepository.getByAuthTypeAndClientId(request.getType(), clientId).isPresent()) {
            throw new ExistingAuthException();
        }

        return tokenProvider.create(
            authRepository.save(
                Auth.builder()
                    .type(request.getType())
                    .clientId(getClientId(request.getType(), request.getToken()))
                    .member(
                        memberRepository.save(
                            Member.builder()
                                .name(securityUtilService.protectInputValue(request.getName()))
                                .email(request.getEmail())
                                .build()
                        )
                    ).build()
            )
        );
    }

    @Override
    public TokenResponse reissue(ReissueRequest request) {
        if (!tokenProvider.validation(request.getRefreshToken())) {
            throw new InvalidTokenException();
        }

        return tokenProvider.create(tokenProvider.parse(request.getRefreshToken()));
    }

    @Override
    public boolean signOut(String memberId) {
        Auth auth = authRepository.getByMemberId(UUID.fromString(memberId))
            .orElseThrow(NotFoundAuthException::new);

        return tokenProvider.remove(auth);
    }

    private String getClientId(AuthType type, String token) {
        if (type == AuthType.kakao) {
            return kakaoService.getClientId(token);
        } else {
            throw new InvalidClientException();
        }
    }
}
