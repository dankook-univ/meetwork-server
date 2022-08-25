package com.github.dankook_univ.meetwork.quiz.infra.http.response;

import com.github.dankook_univ.meetwork.quiz.domain.Quiz;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@NoArgsConstructor
@Component
public class QuizResponse {

    @NotBlank
    UUID id;

    @NotBlank
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

    @Builder
    public QuizResponse(UUID id, String name, LocalDateTime createAt, LocalDateTime updateAt,
        @Nullable Boolean isFinished
    ) {
        this.id = id;
        this.name = name;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.isFinished = isFinished != null;
    }
}
