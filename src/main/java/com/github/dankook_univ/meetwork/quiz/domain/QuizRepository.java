package com.github.dankook_univ.meetwork.quiz.domain;

import com.github.dankook_univ.meetwork.quiz.infra.http.response.QuizResponse;
import java.util.List;
import java.util.Optional;

public interface QuizRepository {

    List<QuizResponse> getQuizListWithJoin(String profileId, String eventId);

    Quiz save(Quiz quiz);

    Optional<Quiz> getById(String quizId);

    List<Quiz> getByEventId(String eventId);

    Optional<Quiz> getByName(String name);

    void delete(Quiz quiz);
}
