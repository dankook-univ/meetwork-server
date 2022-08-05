package com.github.dankook_univ.meetwork.profile.infra.http;

import com.github.dankook_univ.meetwork.profile.application.ProfileServiceImpl;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileUpdateRequest;
import com.github.dankook_univ.meetwork.profile.infra.http.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

	private final ProfileServiceImpl profileService;

	@PatchMapping("/update")
	public ResponseEntity<ProfileResponse> update(
			@ApiIgnore Authentication authentication,
			@Valid ProfileUpdateRequest request
	) {
		return ResponseEntity.ok().body(
				profileService.update(
						authentication.getName(),
						request
				).toResponse()
		);
	}
}
