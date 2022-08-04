package com.github.dankook_univ.meetwork.profile.infra.http;

import com.github.dankook_univ.meetwork.member.application.MemberServiceImpl;
import com.github.dankook_univ.meetwork.profile.application.ProfileServiceImpl;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileRequest;
import com.github.dankook_univ.meetwork.profile.infra.http.response.ProfileResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileServiceImpl profileService;
    private final MemberServiceImpl memberService;

    @PostMapping("/new")
    public ResponseEntity<ProfileResponse> create(
        @ApiIgnore Authentication authentication,
        @Valid ProfileRequest request
    ) {
        return ResponseEntity.ok().body(
            profileService.create(
                memberService.getMe(authentication.getName()),
                request
            ).toResponse()
        );
    }

    @PostMapping("/update")
    public ResponseEntity<ProfileResponse> update(
        @ApiIgnore Authentication authentication,
        @Valid ProfileRequest request
    ) {
        return ResponseEntity.ok().body(
            profileService.update(
                memberService.getMe(authentication.getName()),
                request
            ).toResponse()
        );
    }
}
