package com.github.dankook_univ.meetwork.member.infra.http.response;

import com.github.dankook_univ.meetwork.member.domain.Member;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponse {

    @NotNull
    @NotEmpty
    String id;

    @NotNull
    @NotEmpty
    String name;

    @NotNull
    @NotEmpty
    String email;

    @NotNull
    @NotEmpty
    LocalDateTime createAt;

    @NotNull
    @NotEmpty
    LocalDateTime updateAt;

    @Builder
    public MemberResponse(
        Member member
    ) {
        this.id = member.getId().toString();
        this.name = member.getName();
        this.email = member.getEmail();
        this.createAt = member.getCreatedAt();
        this.updateAt = member.getUpdatedAt();
    }
}
