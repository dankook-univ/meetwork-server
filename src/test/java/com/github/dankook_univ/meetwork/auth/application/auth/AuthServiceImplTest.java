package com.github.dankook_univ.meetwork.auth.application.auth;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.dankook_univ.meetwork.auth.domain.auth.AuthType;
import com.github.dankook_univ.meetwork.auth.infra.http.request.SignUpRequest;
import com.github.dankook_univ.meetwork.auth.infra.http.response.TokenResponse;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AuthServiceImplTest {

    @Autowired
    AuthServiceImpl authService;

    @Test
    @DisplayName("회원가입을 할 수 있어요.")
    void signUp() {
        TokenResponse token = authService.signUp(
            SignUpRequest.builder()
                .type(AuthType.kakao)
                .token(UUID.randomUUID().toString())
                .name("name")
                .email("email@meekwork.io")
                .build()
        );

        assertThat(token).isNotNull();
        assertThat(token).isInstanceOf(TokenResponse.class);
    }
}