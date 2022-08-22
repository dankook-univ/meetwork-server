package com.github.dankook_univ.meetwork.invitation.infra.http;

import com.github.dankook_univ.meetwork.invitation.application.InvitationServiceImpl;
import com.github.dankook_univ.meetwork.invitation.domain.Invitation;
import com.github.dankook_univ.meetwork.invitation.infra.http.request.InvitationCreateRequest;
import com.github.dankook_univ.meetwork.invitation.infra.http.response.InvitationResponse;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
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
@RequestMapping("/api/invitation")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationServiceImpl invitationService;

    @ApiOperation(value = "이벤트 초대", notes = "이벤트에 초대하기")
    @PostMapping("/new")
    public ResponseEntity<Boolean> create(
        @ApiIgnore Authentication authentication,
        @RequestBody @Valid InvitationCreateRequest request
    ) {
        return ResponseEntity.ok().body(
            invitationService.create(authentication.getName(), request)
        );
    }

    @ApiOperation(value = "초대 목록 조회", notes = "초대된 이벤트 목록을 최근순으로 조회하기")
    @GetMapping("/list/{memberId}")
    public ResponseEntity<List<InvitationResponse>> getList(
        @ApiIgnore Authentication authentication,
        @PathVariable("memberId") @NotBlank String memberId
    ) {
        return ResponseEntity.ok().body(
            invitationService.getList(memberId)
                .stream().map(Invitation::toResponse)
                .collect(Collectors.toList())
        );
    }

    @ApiOperation(value = "참가하기", notes = "이벤트에 참가하기")
    @PatchMapping("/join/{eventId}")
    public ResponseEntity<Boolean> join(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @NotBlank String eventId,
        @Valid ProfileCreateRequest request
    ) {
        return ResponseEntity.ok().body(
            invitationService.join(authentication.getName(), eventId, request)
        );
    }

    @ApiOperation(value = "거절하기", notes = "이벤트를 거절하기")
    @DeleteMapping("/refuse/{eventId}")
    public ResponseEntity<Boolean> refuse(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @NotBlank String eventId
    ) {
        return ResponseEntity.ok().body(
            invitationService.delete(authentication.getName(), eventId)
        );
    }
}
