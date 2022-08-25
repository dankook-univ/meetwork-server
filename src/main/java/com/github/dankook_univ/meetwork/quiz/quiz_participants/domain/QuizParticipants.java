package com.github.dankook_univ.meetwork.quiz.quiz_participants.domain;

import com.github.dankook_univ.meetwork.common.domain.Core;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.quiz.domain.Quiz;
import com.github.dankook_univ.meetwork.quiz.quiz_participants.infra.http.response.MyQuizResultResponse;
import com.github.dankook_univ.meetwork.quiz.quiz_participants.infra.http.response.QuizParticipantsResponse;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.util.Assert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizParticipants extends Core {

    @NotNull
    @ColumnDefault("true")
    @Column(nullable = false)
    private final Boolean isFinished = true;

    @ManyToOne(targetEntity = Quiz.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne(targetEntity = Profile.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @NotNull
    @ColumnDefault("0")
    @Column(nullable = false)
    private int count = 0;

    @Transient
    private int ranking;

    @Builder
    public QuizParticipants(
        Quiz quiz,
        Profile profile,
        int count
    ) {
        Assert.notNull(quiz, "quiz must not be null");
        Assert.notNull(profile, "profile must not be null");

        this.quiz = quiz;
        this.profile = profile;
        this.count = count;
    }

    public void addCount() {
        this.count += 1;
    }

    public QuizParticipants setRanking(int ranking) {
        this.ranking = ranking;
        
        return this;
    }

    public QuizParticipantsResponse toResponse() {
        return QuizParticipantsResponse.builder()
            .quizParticipants(this)
            .build();
    }

    public MyQuizResultResponse toMyResponse() {
        return MyQuizResultResponse.builder()
            .quizParticipants(this)
            .ranking(ranking)
            .build();
    }
}
