package com.github.dankook_univ.meetwork.event.infra.http;

import com.github.dankook_univ.meetwork.event.application.EventServiceImpl;
import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventCreateRequest;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventUpdateRequest;
import com.github.dankook_univ.meetwork.event.infra.http.request.ProfileReleaseRequest;
import com.github.dankook_univ.meetwork.event.infra.http.request.UpdateAdminRequest;
import com.github.dankook_univ.meetwork.event.infra.http.response.EventResponse;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
import com.github.dankook_univ.meetwork.profile.infra.http.response.ProfileResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
public class EventController {

    private final EventServiceImpl eventService;

    @ApiOperation(value = "이벤트 조회", notes = "이벤트를 조회할 수 있어요.")
    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> get(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @Valid @NotBlank Long eventId
    ) {
        return ResponseEntity.ok().body(
            eventService.get(Long.getLong(authentication.getName()), eventId).toResponse()
        );
    }

    @ApiOperation(value = "이벤트 목록 조회", notes = "이벤트 목록을 조회할 수 있어요.")
    @GetMapping("/list")
    public ResponseEntity<List<EventResponse>> getList(
        @ApiIgnore Authentication authentication,
        @RequestParam(value = "page", required = false, defaultValue = "1") int page
    ) {
        return ResponseEntity.ok().body(
            eventService.getList(Long.getLong(authentication.getName()), page)
                .stream().map(Event::toResponse)
                .collect(Collectors.toList())
        );
    }

    @ApiOperation(value = "이벤트의 내 프로필 가져오기", notes = "이벤트에 참여한 내 프로필을 가져올 수 있어요.")
    @GetMapping("/me/{eventId}")
    public ResponseEntity<ProfileResponse> getMyProfile(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @Valid @NotBlank Long eventId
    ) {
        return ResponseEntity.ok().body(
            eventService.getMyProfile(Long.getLong(authentication.getName()), eventId).toResponse()
        );
    }

    @ApiOperation(value = "이벤트 참여 프로필 목록 조회", notes = "이벤트에 참여한 회원들의 프로필을 조회할 수 있어요.")
    @GetMapping("/members/{eventId}")
    public ResponseEntity<List<ProfileResponse>> getMemberList(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @Valid @NotBlank Long eventId,
        @RequestParam(value = "adminOnly", required = false) Boolean adminOnly,
        @RequestParam(value = "page", required = false, defaultValue = "1") int page
    ) {
        return ResponseEntity.ok().body(
            eventService.getMemberList(Long.getLong(authentication.getName()), eventId, adminOnly,
                    page)
                .stream().map(Profile::toResponse)
                .collect(Collectors.toList())
        );
    }

    @ApiOperation(value = "이벤트 참여 프로필 조회", notes = "회원들의 프로필을 조회할 수 있어요.")
    @GetMapping("/member/{eventId}/{memberId}")
    public ResponseEntity<ProfileResponse> getMember(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @Valid @NotBlank Long eventId,
        @PathVariable("memberId") @Valid @NotBlank Long memberId
    ) {
        return ResponseEntity.ok().body(
            eventService.getMember(Long.getLong(authentication.getName()), eventId, memberId)
                .toResponse()
        );
    }


    @ApiOperation(value = "이벤트 생성", notes = "이벤트를 생성할 수 있어요.")
    @PostMapping("/new")
    public ResponseEntity<EventResponse> create(
        @ApiIgnore Authentication authentication,
        @Valid EventCreateRequest request
    ) {
        return ResponseEntity.ok().body(
            eventService.create(Long.getLong(authentication.getName()), request).toResponse()
        );
    }

    @ApiOperation(value = "이벤트 코드 중복 확인", notes = "이벤트 코드가 중복되는지 검사해요.")
    @GetMapping("/check/{code}")
    public ResponseEntity<Boolean> checkCode(
        @PathVariable("code") @Valid @NotBlank String code
    ) {
        return ResponseEntity.ok().body(
            eventService.checkExistingCode(code)
        );
    }

    @ApiOperation(value = "코드로 이벤트 참여하기", notes = "이벤트의 코드로 이벤트에 참여할 수 있어요.")
    @PatchMapping("/join/{code}")
    public ResponseEntity<EventResponse> joinCode(
        @ApiIgnore Authentication authentication,
        @PathVariable("code") @Valid @NotBlank String code,
        @Valid ProfileCreateRequest request
    ) {
        return ResponseEntity.ok().body(
            eventService.codeJoin(Long.getLong(authentication.getName()), code, request)
                .toResponse()
        );
    }

    @ApiOperation(value = "이벤트 수정", notes = "이벤트를 수정할 수 있어요.")
    @PatchMapping("/{eventId}")
    public ResponseEntity<EventResponse> update(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @Valid @NotBlank Long eventId,
        @RequestBody @Valid EventUpdateRequest request
    ) {
        return ResponseEntity.ok().body(
            eventService.update(Long.getLong(authentication.getName()), eventId, request)
                .toResponse()
        );
    }

    @ApiOperation(value = "관리자 권한 수정", notes = "관리자의 권한을 변경할 수 있어요.")
    @PatchMapping("/updateAdmin")
    public ResponseEntity<Boolean> updateAdmin(
        @ApiIgnore Authentication authentication,
        @RequestBody @Valid UpdateAdminRequest request
    ) {
        return ResponseEntity.ok().body(
            eventService.updateAdmin(Long.getLong(authentication.getName()), request)
        );
    }

    @ApiOperation(value = "이벤트 나가기", notes = "참가했던 이벤트에서 나갈 수 있어요.")
    @DeleteMapping("/secession/{eventId}")
    public ResponseEntity<Boolean> secession(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @Valid @NotBlank Long eventId
    ) {
        eventService.secession(Long.getLong(authentication.getName()), eventId);
        return ResponseEntity.ok().body(true);
    }

    @ApiOperation(value = "이벤트 방출하기", notes = "관리자는 이벤트에서 참가자를 방출시킬 수 있어요.")
    @PostMapping("/release")
    public ResponseEntity<Boolean> release(
        @ApiIgnore Authentication authentication,
        @RequestBody @Valid ProfileReleaseRequest request
    ) {
        eventService.release(Long.getLong(authentication.getName()), request);
        return ResponseEntity.ok().body(true);
    }


    @ApiOperation(value = "이벤트 삭제", notes = "주최자는 이벤트를 삭제할 수 있어요.")
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Boolean> delete(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @Valid @NotBlank Long eventId
    ) {
        eventService.delete(Long.getLong(authentication.getName()), eventId);
        return ResponseEntity.ok().body(true);
    }
}
