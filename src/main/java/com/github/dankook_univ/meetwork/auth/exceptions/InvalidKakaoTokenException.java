package com.github.dankook_univ.meetwork.auth.exceptions;

public class InvalidKakaoTokenException extends RuntimeException {

    public InvalidKakaoTokenException() {
        super("카카오 토큰 정보를 가져올 수 없습니다.");
    }
}
