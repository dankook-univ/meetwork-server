package com.github.dankook_univ.meetwork.member.infra.http;

import com.github.dankook_univ.meetwork.member.application.MemberServiceImpl;
import com.github.dankook_univ.meetwork.member.infra.http.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class MemberController {

	private final MemberServiceImpl memberService;

	@GetMapping("/me")
	public ResponseEntity<MemberResponse> me(
			@ApiIgnore Authentication authentication
	) {
		return ResponseEntity.ok().body(
				memberService.getById(
						authentication.getName()
				).toResponse()
		);
	}
}
