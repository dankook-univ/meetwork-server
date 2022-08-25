package com.github.dankook_univ.meetwork.profile.infra.http.response;

import com.github.dankook_univ.meetwork.file.infra.http.response.FileResponse;
import com.github.dankook_univ.meetwork.member.infra.http.response.MemberResponse;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileResponse {

    @NotBlank
    String id;

    @NotNull
    MemberResponse member;

    FileResponse profileImage;

    @NotBlank
    String nickname;

    @NotEmpty
    String bio;

    @NotNull
    Boolean isAdmin;

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
        this.profileImage =
            profile.getProfileImage() != null ? profile.getProfileImage().toResponse() : null;
        this.nickname = profile.getNickname();
        this.bio = profile.getBio();
        this.isAdmin = profile.getIsAdmin();
        this.createdAt = profile.getCreatedAt();
        this.updatedAt = profile.getUpdatedAt();
    }
}
