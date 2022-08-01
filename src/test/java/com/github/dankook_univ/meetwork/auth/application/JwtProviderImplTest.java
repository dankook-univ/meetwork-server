package com.github.dankook_univ.meetwork.auth.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.dankook_univ.meetwork.auth.application.token.TokenProviderImpl;
import com.github.dankook_univ.meetwork.auth.domain.auth.Auth;
import com.github.dankook_univ.meetwork.auth.domain.auth.AuthType;
import com.github.dankook_univ.meetwork.auth.domain.token.Token;
import com.github.dankook_univ.meetwork.auth.infra.persistence.AuthRepositoryImpl;
import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.member.infra.persistence.MemberRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class JwtProviderImplTest {

    @Autowired
    private AuthRepositoryImpl authRepository;
    @Autowired
    private MemberRepositoryImpl memberRepository;
    @Autowired
    private TokenProviderImpl jwtProvider;

    @Test
    void create() {
        Token token = jwtProvider.create(
            authRepository.save(
                Auth.builder()
                    .type(AuthType.kakao)
                    .clientId("clientId")
                    .member(
                        memberRepository.save(
                            Member.builder()
                                .name("name")
                                .email("meetwork@dankook-univ.com")
                                .build()
                        )
                    ).build()
            )
        );

        assertThat(token).isInstanceOf(Token.class);
    }
}