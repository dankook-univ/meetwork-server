package com.github.dankook_univ.meetwork.profile.exceptions;

public class ExistingNicknameException extends RuntimeException {

    public ExistingNicknameException() {
        super("닉네임이 이미 존재합니다.");
    }
}
