package com.github.dankook_univ.meetwork.profile.domain;

import com.github.dankook_univ.meetwork.common.domain.Core;
import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.file.domain.File;
import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.profile.infra.http.response.ProfileResponse;
import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(targetEntity = Event.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToOne(targetEntity = File.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "file_id")
    private File profileImage;

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private String nickname;

    @Column
    private String bio;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isAdmin = false;

    @Builder
    public Profile(
        Member member,
        Event event,
        File profileImage,
        String nickname,
        String bio,
        Boolean isAdmin
    ) {
        Assert.notNull(member, "member must not be null");
        Assert.notNull(event, "event must not be null");
        Assert.hasText(nickname, "nickname must not be empty");

        this.member = member;
        this.event = event;
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.bio = bio;
        this.isAdmin = isAdmin;
    }

    public void update(
        @Nullable String nickname, @Nullable String bio, @Nullable Boolean isAdmin
    ) {
        if (nickname != null) {
            this.nickname = nickname.trim().replace(" ", "_");
        }
        if (bio != null) {
            this.bio = bio.trim();
        }
        if (isAdmin != null) {
            this.isAdmin = isAdmin;
        }
    }

    public void updateProfileImage(File file) {
        this.profileImage = file;
    }

    public ProfileResponse toResponse() {
        return ProfileResponse.builder()
            .profile(this)
            .build();
    }
}
