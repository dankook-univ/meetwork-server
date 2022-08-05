package com.github.dankook_univ.meetwork.event.infra.http;

import com.github.dankook_univ.meetwork.event.application.EventServiceImpl;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventCreateRequest;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventUpdateRequest;
import com.github.dankook_univ.meetwork.event.infra.http.response.EventResponse;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

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

	@PostMapping("/new")
	public ResponseEntity<EventResponse> create(
			@ApiIgnore Authentication authentication,
			@Valid EventCreateRequest request
	) {
		return ResponseEntity.ok().body(
				eventService.create(authentication.getName(), request).toResponse()
		);
	}

	@PostMapping("/{eventId}/join")
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

	@DeleteMapping("/{eventId}")
	public ResponseEntity<Boolean> delete(
			@ApiIgnore Authentication authentication,
			@PathVariable("eventId") @NotBlank String eventId
	) {
		eventService.delete(authentication.getName(), eventId);
		return ResponseEntity.ok().body(true);
	}
}
