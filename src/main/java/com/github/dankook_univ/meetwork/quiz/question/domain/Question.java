package com.github.dankook_univ.meetwork.quiz.question.domain;

import com.github.dankook_univ.meetwork.common.domain.Core;
import com.github.dankook_univ.meetwork.quiz.domain.Quiz;
import com.github.dankook_univ.meetwork.quiz.question.infra.http.response.QuestionResponse;
import java.util.List;
import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends Core {

    @NotNull
    @ManyToOne(targetEntity = Quiz.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @NotBlank
    @Column(nullable = false)
    private String content;

    @NotBlank
    @Column(nullable = false)
    private String answer;

    @ElementCollection
    private List<String> choice;

    @Builder
    public Question(
        Quiz quiz,
        String content,
        String answer,
        List<String> choice
    ) {
        Assert.notNull(quiz, "quiz must not be null");
        Assert.hasText(content, "content must not be empty");
        Assert.hasText(answer, "answer must not be empty");
        Assert.noNullElements(choice, "choice must not be null");

        this.quiz = quiz;
        this.content = content;
        this.answer = answer;
        this.choice = choice;
    }

    public void update(@Nullable String content, @Nullable String answer,
        @Nullable List<String> choice) {
        if (content != null) {
            this.content = content.trim();
        }
        if (answer != null) {
            this.answer = answer.trim();
        }
        if (choice != null) {
            this.choice = choice;
        }
    }

    public QuestionResponse toResponse() {
        return QuestionResponse.builder()
            .question(this)
            .build();
    }
}
