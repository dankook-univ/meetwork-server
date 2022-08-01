package com.github.dankook_univ.meetwork.member.infra.http.response;

import com.github.dankook_univ.meetwork.member.domain.Member;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@NoArgsConstructor
@Component
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

    String code;

    @Builder
    public MemberResponse(
        Member member
    ) {
        this.id = member.getId().toString();
        this.name = member.getName();
        this.email = member.getEmail();
        this.code = member.getCode();
    }
}
