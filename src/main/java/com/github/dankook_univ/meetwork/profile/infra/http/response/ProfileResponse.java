package com.github.dankook_univ.meetwork.profile.infra.http.response;

import com.github.dankook_univ.meetwork.file.infra.http.response.FileResponse;
import com.github.dankook_univ.meetwork.member.infra.http.response.MemberResponse;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileResponse {

    @NotNull
    @NotEmpty
    String id;

    @NotNull
    MemberResponse member;

    FileResponse profileImage;

    @NotNull
    @NotEmpty
    String nickname;

    @NotEmpty
    String bio;

    @NotNull
    LocalDateTime createdAt;

    @NotNull
    LocalDateTime updatedAt;


    @Builder
    public ProfileResponse(
        Profile profile
    ) {
        this.id = profile.getId().toString();
        this.member = profile.getMember().toResponse();
        this.profileImage = profile.getProfileImage().toResponse();
        this.nickname = profile.getNickname();
        this.bio = profile.getBio();
        this.createdAt = profile.getCreatedAt();
        this.updatedAt = profile.getUpdatedAt();
    }
}
