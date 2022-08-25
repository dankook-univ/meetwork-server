package com.github.dankook_univ.meetwork.quiz.quiz_participants.infra.http.response;

import com.github.dankook_univ.meetwork.profile.infra.http.response.ProfileResponse;
import com.github.dankook_univ.meetwork.quiz.infra.http.response.QuizResponse;
import com.github.dankook_univ.meetwork.quiz.quiz_participants.domain.QuizParticipants;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Getter
@NoArgsConstructor
@Component
public class MyQuizResultResponse {

    @NotBlank
    String id;

    @NotNull
    QuizResponse quiz;

    @NotNull
    ProfileResponse profile;

    @NotNull
    int count;

    @NotNull
    int ranking;

    @NotNull
    LocalDateTime createAt;

    @NotNull
    LocalDateTime updateAt;


    @Builder
    public MyQuizResultResponse(
        QuizParticipants quizParticipants,
        int ranking

    ) {
        this.id = quizParticipants.getId().toString();
        this.quiz = quizParticipants.getQuiz().toResponse();
        this.profile = quizParticipants.getProfile().toResponse();
        this.count = quizParticipants.getCount();
        this.ranking = ranking;
        this.createAt = quizParticipants.getCreatedAt();
        this.updateAt = quizParticipants.getUpdatedAt();
    }
}

