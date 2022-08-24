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

public interface QuizService {

    Quiz create(String memberId, QuizCreateRequest request);

    Quiz update(String memberId, String quizId, QuizUpdateRequest request);

    List<QuizResponse> getList(String memberId, String eventId);

    QuestionsResponse get(String memberId, String quizId);

    List<Question> participant(String memberId, String quizId);

    Boolean check(String memberId, QuestionCheckRequest request);

    QuizParticipants myResult(String memberId, String quizId);

    List<QuizParticipants> result(String memberId, String quizId);

    void delete(String memberId, String quizId);
}
