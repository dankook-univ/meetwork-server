package com.github.dankook_univ.meetwork.email.infra.http;

import com.github.dankook_univ.meetwork.email.application.EmailServiceImpl;
import com.github.dankook_univ.meetwork.email.infra.http.request.EmailRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailServiceImpl emailService;

    @PostMapping("/{eventId}")
    public ResponseEntity<String> sendEmail(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @Valid @NotEmpty String eventId,
        @RequestBody @Valid EmailRequest request
    ) {
        return ResponseEntity.ok().body(
            emailService.mailSend(authentication.getName(), eventId, request)
        );
    }
}
