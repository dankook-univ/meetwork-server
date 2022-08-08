package com.github.dankook_univ.meetwork.auth.application.kakao;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class KakaoServiceImpl implements KakaoService {

    @Override
    public String getClientId(String token) {
        return token;
    }
}
