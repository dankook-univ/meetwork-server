package com.github.dankook_univ.meetwork.member.domain;

import com.github.dankook_univ.meetwork.common.domain.Core;
import com.github.dankook_univ.meetwork.member.infra.http.response.MemberResponse;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends Core {

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NotNull
    @NotEmpty
    @Email
    @Column(nullable = false)
    private String email;

    @Builder
    public Member(String name, String email) {
        Assert.hasText(name, "name must not be empty");
        Assert.hasText(email, "email must not be empty");

        this.name = name;
        this.email = email;
    }

    public void update(
        @Nullable String name, @Nullable String email
    ) {
        if (name != null) {
            this.name = name;
        }
        if (email != null) {
            this.email = email;
        }
    }

    public MemberResponse toResponse() {
        return MemberResponse.builder()
            .member(this)
            .build();
    }
}
