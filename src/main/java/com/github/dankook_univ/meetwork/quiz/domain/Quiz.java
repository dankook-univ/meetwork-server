package com.github.dankook_univ.meetwork.quiz.domain;

import com.github.dankook_univ.meetwork.common.domain.Core;
import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.quiz.infra.http.response.QuizResponse;
import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz extends Core {

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NotNull
    @ManyToOne(targetEntity = Event.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @Builder
    public Quiz(
        String name,
        Event event
    ) {
        Assert.hasText(name, "name must not be empty");
        Assert.notNull(event, "event must not be null");

        this.name = name.trim();
        this.event = event;
    }

    public void update(@Nullable String name) {
        if (name != null) {
            this.name = name.trim();
        }
    }

    public QuizResponse toResponse() {
        return QuizResponse.builder()
            .quiz(this)
            .build();
    }
}
