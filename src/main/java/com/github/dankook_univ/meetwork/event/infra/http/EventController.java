package com.github.dankook_univ.meetwork.event.infra.http;

import com.github.dankook_univ.meetwork.event.application.EventServiceImpl;
import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventCreateRequest;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventUpdateRequest;
import com.github.dankook_univ.meetwork.event.infra.http.response.EventResponse;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
import com.github.dankook_univ.meetwork.profile.infra.http.response.ProfileResponse;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
public class EventController {

    private final EventServiceImpl eventService;

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> get(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @NotBlank String eventId
    ) {
        return ResponseEntity.ok().body(
            eventService.get(authentication.getName(), eventId).toResponse()
        );
    }

    @GetMapping("/list")
    public ResponseEntity<List<EventResponse>> getList(
        @ApiIgnore Authentication authentication,
        @RequestParam(value = "page", required = false, defaultValue = "1") int page
    ) {
        return ResponseEntity.ok().body(
            eventService.getList(authentication.getName(), page)
                .stream().map(Event::toResponse)
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/members/{eventId}")
    public ResponseEntity<List<ProfileResponse>> getMemberList(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @NotBlank String eventId,
        @RequestParam(value = "page", required = false, defaultValue = "1") int page
    ) {
        return ResponseEntity.ok().body(
            eventService.getMemberList(authentication.getName(), eventId, page)
                .stream().map(Profile::toResponse)
                .collect(Collectors.toList())
        );
    }

    @PostMapping("/new")
    public ResponseEntity<EventResponse> create(
        @ApiIgnore Authentication authentication,
        @Valid EventCreateRequest request
    ) {
        return ResponseEntity.ok().body(
            eventService.create(authentication.getName(), request).toResponse()
        );
    }

    @GetMapping("/check/{code}")
    public ResponseEntity<Boolean> codeJoin(
        @PathVariable("code") @NotBlank String code
    ) {
        return ResponseEntity.ok().body(
            eventService.checkExistingCode(code)
        );
    }

    @PatchMapping("/join/{code}")
    public ResponseEntity<EventResponse> codeJoin(
        @ApiIgnore Authentication authentication,
        @PathVariable("code") @NotBlank String code,
        @Valid ProfileCreateRequest request
    ) {
        return ResponseEntity.ok().body(
            eventService.codeJoin(authentication.getName(), code, request).toResponse()
        );
    }

    @PostMapping("/join/{eventId}")
    public ResponseEntity<EventResponse> join(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @NotBlank String eventId,
        @Valid ProfileCreateRequest request
    ) {
        return ResponseEntity.ok().body(
            eventService.join(authentication.getName(), eventId, request).toResponse()
        );
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventResponse> update(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @NotBlank String eventId,
        @Valid EventUpdateRequest request
    ) {
        return ResponseEntity.ok().body(
            eventService.update(authentication.getName(), eventId, request).toResponse()
        );
    }

    @DeleteMapping("/secession/{eventId}")
    public ResponseEntity<Boolean> secession(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @NotBlank String eventId
    ) {
        eventService.secession(authentication.getName(), eventId);
        return ResponseEntity.ok().body(true);
    }


    @DeleteMapping("/{eventId}")
    public ResponseEntity<Boolean> delete(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @NotBlank String eventId
    ) {
        eventService.delete(authentication.getName(), eventId);
        return ResponseEntity.ok().body(true);
    }
}
