package com.github.dankook_univ.meetwork.member.infra.http.response;

import com.github.dankook_univ.meetwork.member.domain.Member;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponse {

    @NotBlank
    String id;

    @NotBlank
    String name;

    @NotBlank
    String email;

    @NotBlank
    LocalDateTime createAt;

    @NotBlank
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
