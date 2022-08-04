package com.github.dankook_univ.meetwork.profile.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.member.infra.persistence.MemberRepositoryImpl;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProfileServiceImplTest {

    @Autowired
    ProfileServiceImpl profileService;

    @Autowired
    MemberRepositoryImpl memberRepository;

    private Member beforeCreateMember() {
        return memberRepository.save(
            Member.builder()
                .name("name")
                .email("meetwork@meetwork.ac.kr")
                .build()
        );
    }

    private Profile beforeCreateProfile(String nickname, String bio, Boolean isAdmin) {
        Member member = beforeCreateMember();

        return profileService.create(
            member,
            ProfileRequest.builder()
                .nickname(nickname)
                .bio(bio)
                .isAdmin(isAdmin)
                .build()
        );

    }

    @Test
    @DisplayName("프로필을 생성할 수 있어요.")
    void create() {
        Profile profile = beforeCreateProfile("name", "This is bio", false);

        assertThat(profile).isNotNull();
        assertThat(profile).isInstanceOf(Profile.class);
    }

    @Test
    @DisplayName("프로필을 수정할 수 있어요.")
    void update() {
        Profile profile = beforeCreateProfile("name", "This is bio", false);

        Profile updatedProfile = profileService.update(
            profile.getMember(),
            ProfileRequest.builder()
                .nickname("nickname")
                .bio("bio2")
                .build()
        );

        assertThat(updatedProfile.getNickname()).isEqualTo("nickname");
        assertThat(updatedProfile.getBio()).isEqualTo("bio2");
    }
}
