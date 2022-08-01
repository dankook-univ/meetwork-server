package com.github.dankook_univ.meetwork.member.exceptions;

public class NotFoundMemberException extends RuntimeException {

    public NotFoundMemberException() {
        super("유저를 찾을 수 없습니다.");
    }
}
