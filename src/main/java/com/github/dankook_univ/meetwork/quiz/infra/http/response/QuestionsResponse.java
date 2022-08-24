package com.github.dankook_univ.meetwork.quiz.infra.http.response;

import com.github.dankook_univ.meetwork.quiz.domain.Quiz;
import com.github.dankook_univ.meetwork.quiz.question.domain.Question;
import com.github.dankook_univ.meetwork.quiz.question.infra.http.response.QuestionResponse;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class QuestionsResponse {


    @NotNull
    QuizResponse quiz;

    @NotNull
    List<QuestionResponse> questions;


    @Builder
    public QuestionsResponse(
        Quiz quiz, List<Question> questions
    ) {
        this.quiz = quiz.toResponse();
        this.questions = questions.stream().map(Question::toResponse).collect(Collectors.toList());
    }
}
