package com.github.dankook_univ.meetwork.quiz.exceptions;

public class NotParticipantQuizException extends RuntimeException {

    public NotParticipantQuizException() {
        super("아직 퀴즈에 참여하지 않았습니다.");
    }

}
