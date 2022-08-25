package com.github.dankook_univ.meetwork.quiz.infra.http;

import com.github.dankook_univ.meetwork.quiz.application.QuizServiceImpl;
import com.github.dankook_univ.meetwork.quiz.infra.http.request.QuizCreateRequest;
import com.github.dankook_univ.meetwork.quiz.infra.http.request.QuizUpdateRequest;
import com.github.dankook_univ.meetwork.quiz.infra.http.response.QuestionsResponse;
import com.github.dankook_univ.meetwork.quiz.infra.http.response.QuizResponse;
import com.github.dankook_univ.meetwork.quiz.question.domain.Question;
import com.github.dankook_univ.meetwork.quiz.question.infra.http.request.QuestionCheckRequest;
import com.github.dankook_univ.meetwork.quiz.question.infra.http.response.QuestionResponse;
import com.github.dankook_univ.meetwork.quiz.quiz_participants.domain.QuizParticipants;
import com.github.dankook_univ.meetwork.quiz.quiz_participants.infra.http.response.MyQuizResultResponse;
import com.github.dankook_univ.meetwork.quiz.quiz_participants.infra.http.response.QuizParticipantsResponse;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizServiceImpl quizService;

    @ApiOperation(value = "퀴즈 생성하기")
    @PostMapping("/new")
    public ResponseEntity<QuizResponse> create(
        @ApiIgnore Authentication authentication,
        @RequestBody @Valid QuizCreateRequest request
    ) {
        return ResponseEntity.ok().body(
            quizService.create(authentication.getName(), request).toResponse()
        );
    }

    @ApiOperation(value = "퀴즈 수정하기")
    @PatchMapping("/{quizId}")
    public ResponseEntity<QuizResponse> update(
        @ApiIgnore Authentication authentication,
        @PathVariable("quizId") @Valid @NotBlank String quizId,
        @RequestBody @Valid QuizUpdateRequest request
    ) {
        return ResponseEntity.ok().body(
            quizService.update(authentication.getName(), quizId, request).toResponse()
        );
    }

    @ApiOperation(value = "퀴즈 목록 조회")
    @GetMapping("/list/{eventId}")
    public ResponseEntity<List<QuizResponse>> getList(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @Valid @NotBlank String eventId
    ) {
        return ResponseEntity.ok().body(
            quizService.getList(authentication.getName(), eventId)
        );
    }

    @ApiOperation(value = "관리자용 질문 목록 조회", notes = "관리자는 퀴즈 참여없이 질문 목록 조회")
    @GetMapping("/questions/{quizId}")
    public ResponseEntity<QuestionsResponse> getQuestions(
        @ApiIgnore Authentication authentication,
        @PathVariable("quizId") @Valid @NotBlank String quizId
    ) {
        return ResponseEntity.ok().body(
            quizService.get(authentication.getName(), quizId)
        );
    }

    @ApiOperation(value = "퀴즈 참여하기", notes = "참여자는 퀴즈 참여 후, 질문 목록 조회 ")
    @GetMapping("/participant/{quizId}")
    public ResponseEntity<List<QuestionResponse>> participant(
        @ApiIgnore Authentication authentication,
        @PathVariable("quizId") @Valid @NotBlank String quizId
    ) {
        return ResponseEntity.ok().body(
            quizService.participant(authentication.getName(), quizId)
                .stream().map(Question::toResponse)
                .collect(Collectors.toList())
        );
    }

    @ApiOperation(value = "정답 비교하기", notes = "한 문제당 정답을 제출해 count수 올리기")
    @PostMapping("/check")
    public ResponseEntity<Boolean> check(
        @ApiIgnore Authentication authentication,
        @RequestBody @Valid QuestionCheckRequest request
    ) {
        return ResponseEntity.ok().body(
            quizService.check(authentication.getName(), request)
        );
    }

    @ApiOperation(value = "내 퀴즈 결과보기", notes = "내 퀴즈 결과 확인")
    @GetMapping("/result/me/{quizId}")
    public ResponseEntity<MyQuizResultResponse> myResult(
        @ApiIgnore Authentication authentication,
        @PathVariable("quizId") @Valid @NotBlank String quizId
    ) {
        return ResponseEntity.ok().body(
            quizService.myResult(authentication.getName(), quizId).toMyResponse()
        );
    }

    @ApiOperation(value = "퀴즈 결과보기", notes = "관리자와 문제 풀이를 끝낸 참여자는 퀴즈 결과 보기 가능")
    @GetMapping("/result/{quizId}")
    public ResponseEntity<List<QuizParticipantsResponse>> result(
        @ApiIgnore Authentication authentication,
        @PathVariable("quizId") @Valid @NotBlank String quizId
    ) {
        return ResponseEntity.ok().body(
            quizService.result(authentication.getName(), quizId)
                .stream().map(QuizParticipants::toResponse)
                .collect(Collectors.toList())
        );
    }

    @ApiOperation(value = "퀴즈 문제 수 조회", notes = "퀴즈의 문제 수 조회")
    @GetMapping("/count/{quizId}")
    public ResponseEntity<Long> count(
        @ApiIgnore Authentication authentication,
        @PathVariable("quizId") @Valid @NotBlank String quizId
    ) {
        return ResponseEntity.ok().body(
            quizService.count(authentication.getName(), quizId)
        );
    }

    @ApiOperation(value = "퀴즈 삭제하기")
    @DeleteMapping("/{quizId}")
    public ResponseEntity<Boolean> delete(
        @ApiIgnore Authentication authentication,
        @PathVariable("quizId") @Valid @NotBlank String quizId
    ) {
        quizService.delete(authentication.getName(), quizId);
        return ResponseEntity.ok().body(true);
    }
}
