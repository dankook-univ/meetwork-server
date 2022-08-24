package com.github.dankook_univ.meetwork.quiz.question.infra.http.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionCheckRequest {

    String quizId;

    String questionId;

    String answer;
}
