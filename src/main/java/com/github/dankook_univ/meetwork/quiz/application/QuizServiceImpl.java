package com.github.dankook_univ.meetwork.quiz.application;

import com.github.dankook_univ.meetwork.profile.application.ProfileServiceImpl;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.exceptions.NotFoundProfileException;
import com.github.dankook_univ.meetwork.quiz.domain.Quiz;
import com.github.dankook_univ.meetwork.quiz.exceptions.AlreadyParticipantedQuizException;
import com.github.dankook_univ.meetwork.quiz.exceptions.ExistingQuizNameException;
import com.github.dankook_univ.meetwork.quiz.exceptions.NotFoundQuestionException;
import com.github.dankook_univ.meetwork.quiz.exceptions.NotFoundQuizException;
import com.github.dankook_univ.meetwork.quiz.exceptions.NotFoundQuizParticipantsException;
import com.github.dankook_univ.meetwork.quiz.exceptions.NotFoundQuizPermissionException;
import com.github.dankook_univ.meetwork.quiz.exceptions.NotParticipantQuizException;
import com.github.dankook_univ.meetwork.quiz.exceptions.QuestionAndQuizRelationshipException;
import com.github.dankook_univ.meetwork.quiz.infra.http.request.QuizCreateRequest;
import com.github.dankook_univ.meetwork.quiz.infra.http.request.QuizUpdateRequest;
import com.github.dankook_univ.meetwork.quiz.infra.http.response.QuestionsResponse;
import com.github.dankook_univ.meetwork.quiz.infra.http.response.QuizResponse;
import com.github.dankook_univ.meetwork.quiz.infra.persistence.QuizRepositoryImpl;
import com.github.dankook_univ.meetwork.quiz.question.domain.Question;
import com.github.dankook_univ.meetwork.quiz.question.infra.http.request.QuestionCheckRequest;
import com.github.dankook_univ.meetwork.quiz.question.infra.http.request.QuestionInformation;
import com.github.dankook_univ.meetwork.quiz.question.infra.persistence.QuestionRepositoryImpl;
import com.github.dankook_univ.meetwork.quiz.quiz_participants.domain.QuizParticipants;
import com.github.dankook_univ.meetwork.quiz.quiz_participants.infra.persistence.QuizParticipantsRepositoryImpl;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizServiceImpl implements QuizService {

    private final QuizRepositoryImpl quizRepository;
    private final QuizParticipantsRepositoryImpl quizParticipantsRepository;
    private final QuestionRepositoryImpl questionRepository;

    private final ProfileServiceImpl profileService;


    @Override
    @Transactional
    public Quiz create(String memberId, QuizCreateRequest request) {
        Profile profile = profileService.get(memberId, request.getEventId());
        if (!profile.getIsAdmin()) {
            throw new NotFoundQuizPermissionException();
        }

        quizRepository.getByName(request.getName())
            .ifPresent((m) -> {
                throw new ExistingQuizNameException();
            });

        Quiz quiz = quizRepository.save(
            Quiz.builder()
                .name(request.getName())
                .event(profile.getEvent())
                .build()
        );

        request.getQuestions().forEach((question) ->
            questionRepository.save(
                Question.builder()
                    .quiz(quiz)
                    .content(question.getContent())
                    .answer(question.getAnswer())
                    .choice(question.getChoice())
                    .build()
            ));

        return quiz;
    }

    @Override
    @Transactional
    public Quiz update(String memberId, String quizId, QuizUpdateRequest request) {
        Quiz quiz = quizRepository.getById(quizId).orElseThrow(NotFoundQuizException::new);
        Profile profile = profileService.get(memberId, quiz.getEvent().getId().toString());
        if (!profile.getIsAdmin()) {
            throw new NotFoundQuizPermissionException();
        }

        if (!Objects.equals(quiz.getName(), request.getName())) {
            quiz.update(request.getName());
        }
        request.getQuestions().stream()
            .map(
                (it) -> QuestionInformation.builder()
                    .question(questionRepository.getById(it.getQuestionId())
                        .orElseThrow(NotFoundQuestionException::new))
                    .content(it.getContent())
                    .answer(it.getAnswer())
                    .choice(it.getChoice())
                    .build()
            )
            .forEach(
                (it) -> it.getQuestion().update(
                    it.getContent(),
                    it.getAnswer(),
                    it.getChoice()
                )
            );

        return quiz;
    }

    @Override
    public List<QuizResponse> getList(String memberId, String eventId) {
        Profile profile = profileService.get(memberId, eventId);

        return quizRepository.getQuizListWithJoin(profile.getId().toString(), eventId);
    }

    @Override
    public QuestionsResponse get(String memberId, String quizId) {
        Quiz quiz = quizRepository.getById(quizId).orElseThrow(NotFoundQuizException::new);
        Profile profile = profileService.get(memberId, quiz.getEvent().getId().toString());
        if (!profile.getIsAdmin()) {
            throw new NotFoundQuizPermissionException();
        }

        return QuestionsResponse.builder()
            .quiz(quiz)
            .questions(questionRepository.getByQuizId(quizId))
            .build();
    }

    @Override
    @Transactional
    public List<Question> participant(String memberId, String quizId) {
        Quiz quiz = quizRepository.getById(quizId).orElseThrow(NotFoundQuizException::new);
        Profile profile = profileService.get(memberId, quiz.getEvent().getId().toString());

        quizParticipantsRepository.getByProfileIdAndQuizId(profile.getId().toString(), quizId)
            .ifPresent(m -> {
                throw new AlreadyParticipantedQuizException();
            });

        quizParticipantsRepository.save(
            QuizParticipants.builder()
                .quiz(quiz)
                .profile(profile)
                .count(0)
                .build()
        );

        return questionRepository.getByQuizId(quizId);
    }

    @Override
    @Transactional
    public Boolean check(String memberId, QuestionCheckRequest request) {
        Quiz quiz = quizRepository.getById(request.getQuizId())
            .orElseThrow(NotFoundQuizException::new);
        Question question = questionRepository.getById(request.getQuestionId())
            .orElseThrow(NotFoundQuestionException::new);
        if (question.getQuiz() != quiz) {
            throw new QuestionAndQuizRelationshipException();
        }

        Profile profile = profileService.get(memberId, quiz.getEvent().getId().toString());
        QuizParticipants quizParticipants = quizParticipantsRepository.getByProfileIdAndQuizId(
            profile.getId().toString(),
            question.getQuiz().getId().toString()
        ).orElseThrow(NotFoundQuizParticipantsException::new);

        if (Objects.equals(request.getAnswer(), question.getAnswer())) {
            quizParticipants.addCount();
            return true;
        }
        return false;
    }

    @Override
    public QuizParticipants myResult(String memberId, String quizId) {
        Quiz quiz = quizRepository.getById(quizId).orElseThrow(NotFoundQuizException::new);
        Profile profile = profileService.get(memberId, quiz.getEvent().getId().toString());
        QuizParticipants quizParticipants = quizParticipantsRepository.getByProfileIdAndQuizId(
            profile.getId().toString(),
            quizId
        ).orElseThrow(NotFoundQuizParticipantsException::new);

        int ranking = quizParticipantsRepository.getByQuizId(quizId)
            .indexOf(quizParticipants) + 1;

        return quizParticipants.setRanking(ranking);
    }

    @Override
    public List<QuizParticipants> result(String memberId, String quizId) {
        Quiz quiz = quizRepository.getById(quizId).orElseThrow(NotFoundQuizException::new);

        Profile profile = profileService.get(memberId, quiz.getEvent().getId().toString());
        QuizParticipants quizParticipants = quizParticipantsRepository.getByProfileIdAndQuizId(
            profile.getId().toString(),
            quizId
        ).orElseThrow(NotFoundQuizParticipantsException::new);
        if (!profile.getIsAdmin() && !quizParticipants.getIsFinished()) {
            throw new NotParticipantQuizException();
        }

        return quizParticipantsRepository.getByQuizId(quizId);
    }

    @Override
    public Long count(String memberId, String quizId) {
        Quiz quiz = quizRepository.getById(quizId).orElseThrow(NotFoundQuizException::new);

        Boolean isMember = profileService.isEventMember(memberId, quiz.getEvent().getId().toString());
        if(!isMember){
            throw new NotFoundProfileException();
        }

        return questionRepository.countByQuizId(quizId);
    }

    @Override
    @Transactional
    public void delete(String memberId, String quizId) {
        Quiz quiz = quizRepository.getById(quizId).orElseThrow(NotFoundQuizException::new);

        Profile profile = profileService.get(memberId, quiz.getEvent().getId().toString());
        if (!profile.getIsAdmin()) {
            throw new NotFoundQuizPermissionException();
        }

        quizParticipantsRepository.delete(quizId);
        questionRepository.delete(quizId);
        quizRepository.delete(quiz);
    }

}
