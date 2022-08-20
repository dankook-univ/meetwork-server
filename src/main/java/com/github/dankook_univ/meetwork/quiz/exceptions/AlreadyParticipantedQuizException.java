package com.github.dankook_univ.meetwork.quiz.exceptions;

public class AlreadyParticipantedQuizException extends RuntimeException {

    public AlreadyParticipantedQuizException() {
        super("이미 퀴즈에 참가했습니다.");
    }
}
