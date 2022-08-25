package com.github.dankook_univ.meetwork.quiz.application;

import com.github.dankook_univ.meetwork.common.service.SecurityUtilService;
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
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizServiceImpl implements QuizService {

    private final SecurityUtilService securityUtilService;
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
            .ifPresent((it) -> {
                throw new ExistingQuizNameException();
            });

        Quiz quiz = quizRepository.save(
            Quiz.builder()
                .name(securityUtilService.protectInputValue(request.getName()))
                .event(profile.getEvent())
                .build()
        );

        request.getQuestions().forEach((question) ->
            questionRepository.save(
                Question.builder()
                    .quiz(quiz)
                    .content(securityUtilService.protectInputValue(question.getContent()))
                    .answer(securityUtilService.protectInputValue(question.getAnswer()))
                    .choice(
                        question.getChoice().stream()
                            .map(securityUtilService::protectInputValue)
                            .collect(Collectors.toList())
                    )
                    .build()
            )
        );

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

        if (!quiz.getName().equals(request.getName())) {
            quiz.update(request.getName());
        }

        request.getQuestions().stream()
            .map(
                (it) -> QuestionInformation.builder()
                    .question(questionRepository.getById(it.getQuestionId())
                        .orElseThrow(NotFoundQuestionException::new))
                    .content(securityUtilService.protectInputValue(it.getContent()))
                    .answer(securityUtilService.protectInputValue(it.getAnswer()))
                    .choice(
                        it.getChoice().stream()
                            .map(securityUtilService::protectInputValue)
                            .collect(Collectors.toList())
                    )
                    .build()
            )
            .forEach(
                (it) -> it.getQuestion().update(
                    securityUtilService.protectInputValue(it.getContent()),
                    securityUtilService.protectInputValue(it.getAnswer()),
                    it.getChoice().stream()
                        .map(securityUtilService::protectInputValue)
                        .collect(Collectors.toList())
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
            .ifPresent(it -> {
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
        if (!question.getQuiz().getId().equals(quiz.getId())) {
            throw new QuestionAndQuizRelationshipException();
        }

        Profile profile = profileService.get(memberId, quiz.getEvent().getId().toString());
        QuizParticipants quizParticipants = quizParticipantsRepository.getByProfileIdAndQuizId(
            profile.getId().toString(),
            question.getQuiz().getId().toString()
        ).orElseThrow(NotFoundQuizParticipantsException::new);

        if (securityUtilService.protectInputValue(request.getAnswer())
            .equals(question.getAnswer())) {
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
        if (!profile.getIsAdmin()) {
            quizParticipantsRepository.getByProfileIdAndQuizId(
                profile.getId().toString(),
                quizId
            ).orElseThrow(NotParticipantQuizException::new);
        }

        return quizParticipantsRepository.getByQuizId(quizId);
    }

    @Override
    public Long count(String memberId, String quizId) {
        Quiz quiz = quizRepository.getById(quizId).orElseThrow(NotFoundQuizException::new);
        if (!profileService.isEventMember(memberId, quiz.getEvent().getId().toString())) {
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

    @Override
    @Transactional
    public void deleteByEventId(String eventId) {
        quizRepository.getByEventId(eventId)
            .forEach(
                (quiz) -> {
                    quizParticipantsRepository.delete(quiz.getId().toString());
                    questionRepository.delete(quiz.getId().toString());
                    quizRepository.delete(quiz);
                }
            );
    }

}
