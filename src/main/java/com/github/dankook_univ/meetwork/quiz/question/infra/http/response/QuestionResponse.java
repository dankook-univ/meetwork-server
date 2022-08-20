package com.github.dankook_univ.meetwork.quiz.question.infra.http.response;

import com.github.dankook_univ.meetwork.quiz.infra.http.response.QuizResponse;
import com.github.dankook_univ.meetwork.quiz.question.domain.Question;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@NoArgsConstructor
@Component
public class QuestionResponse {

    @NotNull
    @NotEmpty
    String id;

    @NotNull
    @NotEmpty
    String content;

    @NotNull
    @NotEmpty
    String answer;

    @NotNull
    List<String> choice;

    @NotNull
    QuizResponse quiz;

    @NotNull
    LocalDateTime createAt;

    @NotNull
    LocalDateTime updateAt;

    @Builder
    public QuestionResponse(
        Question question
    ) {
        this.id = question.getId().toString();
        this.content = question.getContent();
        this.answer = question.getAnswer();
        this.quiz = question.getQuiz().toResponse();
        this.choice = question.getChoice();
        this.createAt = question.getCreatedAt();
        this.updateAt = question.getUpdatedAt();
    }
}
