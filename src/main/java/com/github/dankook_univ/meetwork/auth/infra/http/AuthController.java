package com.github.dankook_univ.meetwork.auth.infra.http;

import com.github.dankook_univ.meetwork.auth.application.auth.AuthServiceImpl;
import com.github.dankook_univ.meetwork.auth.infra.http.request.ReissueRequest;
import com.github.dankook_univ.meetwork.auth.infra.http.request.SignInRequest;
import com.github.dankook_univ.meetwork.auth.infra.http.request.SignUpRequest;
import com.github.dankook_univ.meetwork.auth.infra.http.response.TokenResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> signIn(@RequestBody @Valid SignInRequest request) {
        return ResponseEntity.ok().body(authService.signIn(request));
    }

    @PostMapping("/new")
    public ResponseEntity<TokenResponse> signUp(@RequestBody @Valid SignUpRequest request) {
        return ResponseEntity.ok().body(authService.signUp(request));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(
        @RequestBody @Valid ReissueRequest request
    ) {
        return ResponseEntity.ok().body(authService.reissue(request));
    }

    @PutMapping("/logout")
    public ResponseEntity<Boolean> signOut(@ApiIgnore Authentication authentication) {
        return ResponseEntity.ok().body(authService.signOut(authentication.getName()));
    }
}
