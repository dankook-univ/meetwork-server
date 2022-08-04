package com.github.dankook_univ.meetwork.profile.domain;

import com.github.dankook_univ.meetwork.common.domain.Core;
import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.profile.infra.http.response.ProfileResponse;
import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.util.Assert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends Core {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private String nickname;

    @NotEmpty
    @Column(nullable = true)
    private String bio;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isAdmin = false;

    @Builder
    public Profile(Member member, String nickname, String bio, Boolean isAdmin) {
        Assert.notNull(member, "member must not be null");
        Assert.hasText(nickname, "nickname must not be empty");

        this.member = member;
        this.nickname = nickname;
        this.bio = bio;
        this.isAdmin = isAdmin;
    }

    public void update(
        @Nullable String nickname, @Nullable String bio
    ) {
        if (nickname != null) {
            this.nickname = nickname;
        }
        if (bio != null) {
            this.bio = bio;
        }
    }

    public void changeAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public ProfileResponse toResponse() {
        return ProfileResponse.builder()
            .profile(this)
            .build();
    }
}
