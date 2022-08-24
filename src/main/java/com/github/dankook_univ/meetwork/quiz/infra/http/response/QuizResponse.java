package com.github.dankook_univ.meetwork.quiz.infra.http.response;

import com.github.dankook_univ.meetwork.quiz.domain.Quiz;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
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
public class QuizResponse {

    @NotNull
    @NotEmpty
    UUID id;

    @NotNull
    @NotEmpty
    String name;

    @NotNull
    LocalDateTime createAt;

    @NotNull
    LocalDateTime updateAt;

    @NotNull
    Boolean isFinished;

    @Builder
    public QuizResponse(
        Quiz quiz
    ) {
        this.id = quiz.getId();
        this.name = quiz.getName();
        this.createAt = quiz.getCreatedAt();
        this.updateAt = quiz.getUpdatedAt();
    }
}
