package com.github.dankook_univ.meetwork.quiz.application;

import com.github.dankook_univ.meetwork.quiz.domain.Quiz;
import com.github.dankook_univ.meetwork.quiz.infra.http.request.QuizCreateRequest;
import com.github.dankook_univ.meetwork.quiz.infra.http.request.QuizUpdateRequest;
import com.github.dankook_univ.meetwork.quiz.infra.http.response.QuestionsResponse;
import com.github.dankook_univ.meetwork.quiz.infra.http.response.QuizResponse;
import com.github.dankook_univ.meetwork.quiz.question.domain.Question;
import com.github.dankook_univ.meetwork.quiz.question.infra.http.request.QuestionCheckRequest;
import com.github.dankook_univ.meetwork.quiz.quiz_participants.domain.QuizParticipants;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface QuizService {

    Quiz create(Long memberId, QuizCreateRequest request);

    Quiz update(Long memberId, Long quizId, QuizUpdateRequest request);

    List<QuizResponse> getList(Long memberId, Long eventId);

    QuestionsResponse get(Long memberId, Long quizId);

    List<Question> participant(Long memberId, Long quizId);

    Boolean check(Long memberId, QuestionCheckRequest request);

    QuizParticipants myResult(Long memberId, Long quizId);

    List<QuizParticipants> result(Long memberId, Long quizId);

    Long count(Long memberId, Long quizId);

    void delete(Long memberId, Long quizId);

    @Transactional
    void deleteByEventId(Long eventId);
}
