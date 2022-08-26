package com.github.dankook_univ.meetwork.email.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.dankook_univ.meetwork.email.infra.http.request.EmailRequest;
import com.github.dankook_univ.meetwork.event.application.EventServiceImpl;
import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventCreateRequest;
import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.member.infra.persistence.MemberRepositoryImpl;
import com.github.dankook_univ.meetwork.profile.application.ProfileServiceImpl;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
import com.github.dankook_univ.meetwork.quiz.application.QuizServiceImpl;
import com.github.dankook_univ.meetwork.quiz.domain.Quiz;
import com.github.dankook_univ.meetwork.quiz.infra.http.request.QuizCreateRequest;
import com.github.dankook_univ.meetwork.quiz.question.domain.Question;
import com.github.dankook_univ.meetwork.quiz.question.infra.http.request.QuestionCheckRequest;
import com.github.dankook_univ.meetwork.quiz.question.infra.http.request.QuestionCreateRequest;
import com.github.dankook_univ.meetwork.quiz.question.infra.persistence.QuestionRepositoryImpl;
import com.github.dankook_univ.meetwork.quiz.quiz_participants.domain.QuizParticipants;
import com.github.dankook_univ.meetwork.quiz.quiz_participants.infra.persistence.QuizParticipantsRepositoryImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailServiceImplTest {

    @Autowired
    EmailServiceImpl emailService;

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
    public void sendMail() {
        Member admin = createMember("admin", "meetwork@meetwork.kr");
        Event event = createEvent(admin);

        Member participant = createMember("name", "govl6113@naver.com");
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

        quizService.check(
            admin.getId().toString(),
            QuestionCheckRequest.builder()
                .quizId(quiz.getId().toString())
                .questionId(question.get(0).getId().toString())
                .answer("answer").build()
        );
        quizService.check(
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

        String memberEmail = emailService.mailSend(
            participant.getId().toString(),
            event.getId().toString(),
            EmailRequest.builder()
                .title("meetwork")
                .content("paticipant 님은 00")
                .build()
        );

        assertThat(memberEmail).isNotBlank();
    }
}