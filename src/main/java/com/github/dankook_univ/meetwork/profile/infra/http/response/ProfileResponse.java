package com.github.dankook_univ.meetwork.profile.infra.http.response;

import com.github.dankook_univ.meetwork.member.infra.http.response.MemberResponse;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@NoArgsConstructor
@Component
public class ProfileResponse {

    @NotNull
    @NotEmpty
    String id;

    @NotNull
    MemberResponse member;

    @NotNull
    @NotEmpty
    String nickname;

    @NotEmpty
    String bio;

    @NotNull
    LocalDateTime createAt;

    @NotNull
    LocalDateTime updateAt;


    @Builder
    public ProfileResponse(
        Profile profile
    ) {
        this.id = profile.getId().toString();
        this.member = profile.getMember().toResponse();
        this.nickname = profile.getNickname();
        this.bio = profile.getBio();
        this.createAt = profile.getCreatedAt();
        this.updateAt = profile.getUpdatedAt();
    }
}
