package com.github.dankook_univ.meetwork.auth.infra.http.request;

import com.github.dankook_univ.meetwork.auth.domain.auth.AuthType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {

    @NotBlank
    private AuthType type;

    @NotBlank
    private String token;

    @NotBlank
    @Length(min = 2)
    private String name;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    private String email;

    @Builder
    public SignUpRequest(
        AuthType type,
        String token,
        String name,
        String email
    ) {
        this.type = type;
        this.token = token;
        this.name = name;
        this.email = email;
    }
}
