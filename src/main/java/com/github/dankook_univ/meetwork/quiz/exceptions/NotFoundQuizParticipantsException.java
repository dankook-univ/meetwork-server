package com.github.dankook_univ.meetwork.quiz.exceptions;

public class NotFoundQuizParticipantsException extends RuntimeException {

    public NotFoundQuizParticipantsException() {
        super("퀴즈 참여자 프로필이 존재하지 않습니다.");
    }
}
