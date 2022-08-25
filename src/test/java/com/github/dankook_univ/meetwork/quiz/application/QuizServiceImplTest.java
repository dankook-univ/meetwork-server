package com.github.dankook_univ.meetwork.quiz.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.dankook_univ.meetwork.event.application.EventServiceImpl;
import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventCreateRequest;
import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.member.infra.persistence.MemberRepositoryImpl;
import com.github.dankook_univ.meetwork.profile.application.ProfileServiceImpl;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
import com.github.dankook_univ.meetwork.quiz.domain.Quiz;
import com.github.dankook_univ.meetwork.quiz.exceptions.AlreadyParticipantedQuizException;
import com.github.dankook_univ.meetwork.quiz.exceptions.NotFoundQuestionException;
import com.github.dankook_univ.meetwork.quiz.exceptions.NotFoundQuizException;
import com.github.dankook_univ.meetwork.quiz.exceptions.NotFoundQuizParticipantsException;
import com.github.dankook_univ.meetwork.quiz.exceptions.NotParticipantQuizException;
import com.github.dankook_univ.meetwork.quiz.infra.http.request.QuizCreateRequest;
import com.github.dankook_univ.meetwork.quiz.infra.http.request.QuizUpdateRequest;
import com.github.dankook_univ.meetwork.quiz.infra.http.response.QuizResponse;
import com.github.dankook_univ.meetwork.quiz.question.domain.Question;
import com.github.dankook_univ.meetwork.quiz.question.infra.http.request.QuestionCheckRequest;
import com.github.dankook_univ.meetwork.quiz.question.infra.http.request.QuestionCreateRequest;
import com.github.dankook_univ.meetwork.quiz.question.infra.http.request.QuestionUpdateRequest;
import com.github.dankook_univ.meetwork.quiz.question.infra.persistence.QuestionRepositoryImpl;
import com.github.dankook_univ.meetwork.quiz.quiz_participants.domain.QuizParticipants;
import com.github.dankook_univ.meetwork.quiz.quiz_participants.infra.persistence.QuizParticipantsRepositoryImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class QuizServiceImplTest {

    @Autowired
    QuizServiceImpl quizService;

    @Autowired
    QuestionRepositoryImpl questionRepository;

    @Autowired
    QuizParticipantsRepositoryImpl quizParticipantsRepository;

    @Autowired
    MemberRepositoryImpl memberRepository;

    @Autowired
    EventServiceImpl eventService;

    @Autowired
    ProfileServiceImpl profileService;


    private Member createMember(String name, String email) {
        return memberRepository.save(
            Member.builder()
                .name(name)
                .email(email)
                .build()
        );
    }

    private Event createEvent(Member member) {
        return eventService.create(
            member.getId().toString(),
            EventCreateRequest.builder()
                .name("eventName")
                .organizerNickname("nickname")
                .organizerBio("bio")
                .code("code")
                .build()
        );
    }


    @Test
    @DisplayName("관리자는 퀴즈를 생성할 수 있어요.")
    public void create() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);

        List<QuestionCreateRequest> list = new ArrayList<QuestionCreateRequest>();
        list.add(QuestionCreateRequest.builder()
            .content("question1")
            .answer("answer1")
            .choice(Arrays.asList("choice1", "choice2", "answer1", "choice3"))
            .build());
        list.add(QuestionCreateRequest.builder()
            .content("question2")
            .answer("answer2")
            .choice(Arrays.asList("choice1", "answer2", "choice2", "choice3"))
            .build());

        Quiz quiz = quizService.create(
            member.getId().toString(),
            QuizCreateRequest.builder()
                .name("quizName")
                .eventId(event.getId().toString())
                .questions(list)
                .build());
        List<Question> questions = questionRepository.getByQuizId(quiz.getId().toString());

        assertThat(quiz).isNotNull().isInstanceOf(Quiz.class);
        assertThat(quiz.getEvent()).isEqualTo(event);

        assertThat(questions.size()).isEqualTo(2);
        assertThat(quiz.toResponse()).isNotNull();
    }

    @Test
    @DisplayName("관리자는 퀴즈를 수정할 수 있어요.")
    public void update() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);

        Quiz quiz = quizService.create(
            member.getId().toString(),
            QuizCreateRequest.builder()
                .name("quizName1")
                .eventId(event.getId().toString())
                .questions(
                    List.of(QuestionCreateRequest.builder()
                        .content("question1")
                        .answer("answer1")
                        .choice(Arrays.asList("choice1", "choice2", "answer1", "choice3"))
                        .build()
                    )
                )
                .build());
        List<Question> question = questionRepository.getByQuizId(quiz.getId().toString());

        assertThat(quiz).isNotNull();
        assertThat(quiz.getName()).isEqualTo("quizName1");
        assertThat(question.get(0).getContent()).isEqualTo("question1");

        Quiz updatedQuiz = quizService.update(
            member.getId().toString(),
            quiz.getId().toString(),
            QuizUpdateRequest.builder()
                .name("quizName2")
                .questions(
                    List.of(QuestionUpdateRequest.builder()
                        .questionId(question.get(0).getId().toString())
                        .content("question1-1")
                        .answer("answer1-1")
                        .choice(Arrays.asList("choice1", "choice2", "answer1-1", "choice3"))
                        .build()
                    )
                )
                .build()
        );
        List<Question> updatedQuestion = questionRepository.getByQuizId(quiz.getId().toString());

        assertThat(updatedQuiz.getName()).isEqualTo("quizName2");
        assertThat(updatedQuestion.get(0).getContent()).isEqualTo("question1-1");
    }


    @Test
    @DisplayName("퀴즈 목록을 가져올 수 있어요.")
    public void getList() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);

        quizService.create(
            member.getId().toString(),
            QuizCreateRequest.builder()
                .name("quizName1")
                .eventId(event.getId().toString())
                .questions(
                    List.of(QuestionCreateRequest.builder()
                        .content("question1")
                        .answer("answer1")
                        .choice(Arrays.asList("choice1", "choice2", "answer1", "choice3"))
                        .build()))
                .build());

        quizService.create(
            member.getId().toString(),
            QuizCreateRequest.builder()
                .name("quizName2")
                .eventId(event.getId().toString())
                .questions(
                    List.of(QuestionCreateRequest.builder()
                        .content("question2")
                        .answer("answer2")
                        .choice(Arrays.asList("choice1", "choice2", "answer2", "choice3"))
                        .build()))
                .build());

        List<QuizResponse> list = quizService.getList(
            member.getId().toString(),
            event.getId().toString()
        );

        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0)).isNotNull().isInstanceOf(QuizResponse.class);
    }

    @Test
    @DisplayName("퀴즈에 참여하지 않은 사람도 목록을 가져올 수 있어요.")
    public void getListWithNoParticipant() {
        Member organizer = createMember("organizer", "meetwork@meetwork.kr");
        Event event = createEvent(organizer);

        Member participant = createMember("name", "meetwork@meetwork.kr");
        eventService.join(
            participant.getId().toString(),
            event.getId().toString(),
            ProfileCreateRequest.builder()
                .nickname("nickname123")
                .bio("bio")
                .build(),
            false);

        Quiz quiz = quizService.create(
            organizer.getId().toString(),
            QuizCreateRequest.builder()
                .name("quizName2")
                .eventId(event.getId().toString())
                .questions(
                    List.of(QuestionCreateRequest.builder()
                        .content("question2")
                        .answer("answer2")
                        .choice(Arrays.asList("choice1", "choice2", "answer2", "choice3"))
                        .build()))
                .build());

        List<QuizResponse> list = quizService.getList(
            participant.getId().toString(),
            event.getId().toString()
        );

        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0)).isNotNull().isInstanceOf(QuizResponse.class);

        assertThat(list.get(0).getIsFinished()).isFalse();

        quizService.participant(participant.getId().toString(), quiz.getId().toString());

        List<QuizResponse> participantList = quizService.getList(
            participant.getId().toString(),
            event.getId().toString()
        );

        assertThat(participantList.get(0).getIsFinished()).isTrue();
    }

    @Test
    @DisplayName("퀴즈에 참여할 수 있습니다.")
    public void participant() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);

        Quiz quiz = quizService.create(
            member.getId().toString(),
            QuizCreateRequest.builder()
                .name("quizName")
                .eventId(event.getId().toString())
                .questions(
                    List.of(QuestionCreateRequest.builder()
                        .content("question")
                        .answer("answer")
                        .choice(Arrays.asList("choice1", "choice2", "answer", "choice3"))
                        .build()))
                .build());

        List<Question> question = quizService.participant(
            member.getId().toString(),
            quiz.getId().toString()
        );

        assertThat(question.size()).isEqualTo(1);
        assertThat(question.get(0).getAnswer()).isEqualTo("answer");

        Profile profile = profileService.get(member.getId().toString(), event.getId().toString());
        QuizParticipants quizParticipants = quizParticipantsRepository.getByProfileIdAndQuizId(
            profile.getId().toString(),
            quiz.getId().toString()
        ).orElseThrow(NotParticipantQuizException::new);

        assertThat(quizParticipants).isNotNull().isInstanceOf(QuizParticipants.class);
        assertThat(quizParticipants.getQuiz()).isEqualTo(quiz);
        assertThat(quizParticipants.getProfile()).isEqualTo(profile);
        assertThat(quizParticipants.getCount()).isEqualTo(0);
        assertThat(quizParticipants.getIsFinished()).isTrue();
    }

    @Test
    @DisplayName("이미 퀴즈에 참여했다면, 다시 참여할 수 없습니다.")
    public void failedParticipant() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);

        Quiz quiz = quizService.create(
            member.getId().toString(),
            QuizCreateRequest.builder()
                .name("quizName")
                .eventId(event.getId().toString())
                .questions(
                    List.of(QuestionCreateRequest.builder()
                        .content("question")
                        .answer("answer")
                        .choice(Arrays.asList("choice1", "choice2", "answer", "choice3"))
                        .build()))
                .build());

        quizService.participant(
            member.getId().toString(),
            quiz.getId().toString()
        );

        Assertions.assertThrows(AlreadyParticipantedQuizException.class, () -> {
            quizService.participant(
                member.getId().toString(),
                quiz.getId().toString()
            );
        });
    }

    @Test
    @DisplayName("퀴즈 정답을 맞추면 count가 올라갑니다.")
    public void countCheck() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);

        Quiz quiz = quizService.create(
            member.getId().toString(),
            QuizCreateRequest.builder()
                .name("quizName")
                .eventId(event.getId().toString())
                .questions(
                    List.of(QuestionCreateRequest.builder()
                        .content("question")
                        .answer("answer")
                        .choice(Arrays.asList("choice1", "choice2", "answer", "choice3"))
                        .build()))
                .build());

        List<Question> question = quizService.participant(
            member.getId().toString(),
            quiz.getId().toString()
        );

        Boolean check = quizService.check(
            member.getId().toString(),
            QuestionCheckRequest.builder()
                .quizId(quiz.getId().toString())
                .questionId(question.get(0).getId().toString())
                .answer("answer").build()
        );

        assertThat(question.size()).isEqualTo(1);
        assertThat(question.get(0)).isNotNull();

        Profile profile = profileService.get(member.getId().toString(), event.getId().toString());
        QuizParticipants quizParticipants = quizParticipantsRepository.getByProfileIdAndQuizId(
            profile.getId().toString(),
            quiz.getId().toString()
        ).orElseThrow(NotParticipantQuizException::new);

        assertThat(quizParticipants).isNotNull().isInstanceOf(QuizParticipants.class);
        assertThat(check).isTrue();
        assertThat(quizParticipants.getCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("퀴즈 정답을 틀리면 count가 올라가지 않습니다.")
    public void noCountCheck() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);

        Quiz quiz = quizService.create(
            member.getId().toString(),
            QuizCreateRequest.builder()
                .name("quizName")
                .eventId(event.getId().toString())
                .questions(
                    List.of(QuestionCreateRequest.builder()
                        .content("question")
                        .answer("answer")
                        .choice(Arrays.asList("choice1", "choice2", "answer", "choice3"))
                        .build()))
                .build());

        List<Question> question = quizService.participant(
            member.getId().toString(),
            quiz.getId().toString()
        );

        Boolean check = quizService.check(
            member.getId().toString(),
            QuestionCheckRequest.builder()
                .quizId(quiz.getId().toString())
                .questionId(question.get(0).getId().toString())
                .answer("noAnswer").build()
        );

        assertThat(question.size()).isEqualTo(1);
        assertThat(question.get(0)).isNotNull();

        Profile profile = profileService.get(member.getId().toString(), event.getId().toString());
        QuizParticipants quizParticipants = quizParticipantsRepository.getByProfileIdAndQuizId(
            profile.getId().toString(),
            quiz.getId().toString()
        ).orElseThrow(NotParticipantQuizException::new);

        assertThat(quizParticipants).isNotNull().isInstanceOf(QuizParticipants.class);
        assertThat(check).isFalse();
        assertThat(quizParticipants.getCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("퀴즈 참여자는 본인의 점수 및 순위를 볼 수 있어요.")
    public void myResult() {
        Member admin = createMember("admin", "meetwork@meetwork.kr");
        Event event = createEvent(admin);

        Member participant = createMember("name", "meetwork@meetwork.kr");
        eventService.join(
            participant.getId().toString(),
            event.getId().toString(),
            ProfileCreateRequest.builder()
                .nickname("participant")
                .bio("bio")
                .build(),
            false
        );

        Quiz quiz = quizService.create(
            admin.getId().toString(),
            QuizCreateRequest.builder()
                .name("quizName")
                .eventId(event.getId().toString())
                .questions(
                    List.of(QuestionCreateRequest.builder()
                        .content("question")
                        .answer("answer")
                        .choice(Arrays.asList("choice1", "choice2", "answer", "choice3"))
                        .build()))
                .build());
        List<Question> question = quizService.participant(
            admin.getId().toString(),
            quiz.getId().toString()
        );
        quizService.participant(
            participant.getId().toString(),
            quiz.getId().toString()
        );

        Boolean checkTrue = quizService.check(
            admin.getId().toString(),
            QuestionCheckRequest.builder()
                .quizId(quiz.getId().toString())
                .questionId(question.get(0).getId().toString())
                .answer("answer").build()
        );

        Boolean checkFalse = quizService.check(
            participant.getId().toString(),
            QuestionCheckRequest.builder()
                .quizId(quiz.getId().toString())
                .questionId(question.get(0).getId().toString())
                .answer("noAnswer").build()
        );

        QuizParticipants result = quizService.myResult(
            participant.getId().toString(),
            quiz.getId().toString()
        );

        assertThat(result).isNotNull();
        assertThat(checkTrue).isTrue();
        assertThat(checkFalse).isFalse();
        assertThat(result.getCount()).isEqualTo(0);
        assertThat(result.getRanking()).isEqualTo(2);
    }

    @Test
    @DisplayName("퀴즈 참여자는 참여자들의 순위를 볼 수 있어요.")
    public void resultWithParticipants() {
        Member admin = createMember("admin", "meetwork@meetwork.kr");
        Event event = createEvent(admin);

        Member participant = createMember("name", "meetwork@meetwork.kr");
        eventService.join(
            participant.getId().toString(),
            event.getId().toString(),
            ProfileCreateRequest.builder()
                .nickname("participant")
                .bio("bio")
                .build(),
            false
        );

        Quiz quiz = quizService.create(
            admin.getId().toString(),
            QuizCreateRequest.builder()
                .name("quizName")
                .eventId(event.getId().toString())
                .questions(
                    List.of(QuestionCreateRequest.builder()
                        .content("question")
                        .answer("answer")
                        .choice(Arrays.asList("choice1", "choice2", "answer", "choice3"))
                        .build()))
                .build());
        List<Question> question = quizService.participant(
            admin.getId().toString(),
            quiz.getId().toString()
        );
        quizService.participant(
            participant.getId().toString(),
            quiz.getId().toString()
        );

        Boolean checkTrue = quizService.check(
            admin.getId().toString(),
            QuestionCheckRequest.builder()
                .quizId(quiz.getId().toString())
                .questionId(question.get(0).getId().toString())
                .answer("answer").build()
        );

        Boolean checkFalse = quizService.check(
            participant.getId().toString(),
            QuestionCheckRequest.builder()
                .quizId(quiz.getId().toString())
                .questionId(question.get(0).getId().toString())
                .answer("noAnswer").build()
        );

        List<QuizParticipants> result = quizService.result(
            participant.getId().toString(),
            quiz.getId().toString()
        );

        assertThat(result).isNotNull();
        assertThat(result.get(0).getProfile().getMember()).isEqualTo(admin);
        assertThat(result.get(0).getCount()).isEqualTo(1);
        assertThat(checkTrue).isTrue();
        assertThat(result.get(1).getProfile().getMember()).isEqualTo(participant);
        assertThat(result.get(1).getCount()).isEqualTo(0);
        assertThat(checkFalse).isFalse();
    }


    @Test
    @DisplayName("관리자는 퀴즈 참여자들의 순위를 볼 수 있어요.")
    public void resultWithAdmin() {
        Member admin = createMember("admin", "meetwork@meetwork.kr");
        Event event = createEvent(admin);

        Member participant1 = createMember("participant1", "meetwork@meetwork.kr");
        eventService.join(
            participant1.getId().toString(),
            event.getId().toString(),
            ProfileCreateRequest.builder()
                .nickname("participant1")
                .bio("bio")
                .build(),
            false
        );
        Member participant2 = createMember("participant2", "meetwork@meetwork.kr");
        eventService.join(
            participant2.getId().toString(),
            event.getId().toString(),
            ProfileCreateRequest.builder()
                .nickname("participant2")
                .bio("bio")
                .build(),
            false
        );

        Quiz quiz = quizService.create(
            admin.getId().toString(),
            QuizCreateRequest.builder()
                .name("quizName")
                .eventId(event.getId().toString())
                .questions(
                    List.of(
                        QuestionCreateRequest.builder()
                            .content("question1")
                            .answer("answer")
                            .choice(Arrays.asList("choice1", "choice2", "answer", "choice3"))
                            .build(),
                        QuestionCreateRequest.builder()
                            .content("question2")
                            .answer("answer")
                            .choice(Arrays.asList("choice1", "choice2", "answer", "choice3"))
                            .build()
                    )
                )
                .build());
        List<Question> question = quizService.participant(
            admin.getId().toString(),
            quiz.getId().toString()
        );

        quizService.participant(
            participant1.getId().toString(),
            quiz.getId().toString()
        );
        quizService.participant(
            participant2.getId().toString(),
            quiz.getId().toString()
        );

        quizService.check(
            participant1.getId().toString(),
            QuestionCheckRequest.builder()
                .quizId(quiz.getId().toString())
                .questionId(question.get(0).getId().toString())
                .answer("answer").build()
        );
        quizService.check(
            participant2.getId().toString(),
            QuestionCheckRequest.builder()
                .quizId(quiz.getId().toString())
                .questionId(question.get(0).getId().toString())
                .answer("noAnswer").build()
        );
        quizService.check(
            participant1.getId().toString(),
            QuestionCheckRequest.builder()
                .quizId(quiz.getId().toString())
                .questionId(question.get(1).getId().toString())
                .answer("answer").build()
        );
        quizService.check(
            participant2.getId().toString(),
            QuestionCheckRequest.builder()
                .quizId(quiz.getId().toString())
                .questionId(question.get(1).getId().toString())
                .answer("answer").build()
        );

        List<QuizParticipants> result = quizService.result(
            admin.getId().toString(),
            quiz.getId().toString()
        );

        assertThat(result).isNotNull();
        assertThat(result.get(0).getProfile().getMember()).isEqualTo(participant1);
        assertThat(result.get(0).getCount()).isEqualTo(2);
        assertThat(result.get(1).getProfile().getMember()).isEqualTo(participant2);
        assertThat(result.get(1).getCount()).isEqualTo(1);
    }


    @Test
    @DisplayName("관리자는 퀴즈를 삭제할 수 있어요.")
    public void delete() {
        Member admin = createMember("admin", "meetwork@meetwork.kr");
        Event event = createEvent(admin);

        Member participant = createMember("name", "meetwork@meetwork.kr");
        eventService.join(
            participant.getId().toString(),
            event.getId().toString(),
            ProfileCreateRequest.builder()
                .nickname("participant")
                .bio("bio")
                .build(),
            false
        );

        Quiz quiz = quizService.create(
            admin.getId().toString(),
            QuizCreateRequest.builder()
                .name("quizName")
                .eventId(event.getId().toString())
                .questions(
                    List.of(QuestionCreateRequest.builder()
                        .content("question")
                        .answer("answer")
                        .choice(Arrays.asList("choice1", "choice2", "answer", "choice3"))
                        .build()))
                .build());
        List<Question> question = quizService.participant(
            admin.getId().toString(),
            quiz.getId().toString()
        );

        quizService.participant(
            participant.getId().toString(),
            quiz.getId().toString()
        );

        quizService.delete(admin.getId().toString(), quiz.getId().toString());

        Assertions.assertThrows(NotFoundQuizParticipantsException.class, () -> {
            Profile participantProfile = profileService.get(
                participant.getId().toString(),
                event.getId().toString());
            QuizParticipants participants = quizParticipantsRepository.getByProfileIdAndQuizId(
                participantProfile.getId().toString(),
                quiz.getId().toString()
            ).orElseThrow(NotFoundQuizParticipantsException::new);
        });

        Assertions.assertThrows(NotFoundQuestionException.class, () -> {
            questionRepository.getById(question.get(0).getId().toString())
                .orElseThrow(NotFoundQuestionException::new);
        });

        Assertions.assertThrows(NotFoundQuizException.class, () -> {
            quizService.get(admin.getId().toString(), quiz.getId().toString());
        });

    }
}
