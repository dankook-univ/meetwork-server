package com.github.dankook_univ.meetwork.quiz.question.infra.http.request;

import com.github.dankook_univ.meetwork.quiz.question.domain.Question;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionInformation {

    Question question;

    String content;

    String answer;

    List<String> choice;
}
