package com.github.dankook_univ.meetwork.auth.domain.token;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token implements Serializable {

    @NotNull
    @NotEmpty
    private String accessToken;

    @NotNull
    @NotEmpty
    private String refreshToken;

    @Builder
    public Token(String accessToken, String refreshToken) {
        Assert.hasText(accessToken, "accessToken must not be empty");
        Assert.hasText(refreshToken, "refreshToken must not be empty");

        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
