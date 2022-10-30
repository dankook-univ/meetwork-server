package com.github.dankook_univ.meetwork.profile.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.dankook_univ.meetwork.event.application.EventServiceImpl;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventCreateRequest;
import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.member.infra.persistence.MemberRepositoryImpl;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileUpdateRequest;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class ProfileServiceImplTest {

    @Autowired
    ProfileServiceImpl profileService;

    @Autowired
    MemberRepositoryImpl memberRepository;

    @Autowired
    EventServiceImpl eventService;

    @Test
    @DisplayName("프로필을 생성할 수 있어요.")
    void create() {
        Member member = memberRepository.save(
            Member.builder()
                .name("name")
                .email("meetwork@meetwork.kr")
                .build()
        );

        Profile profile = profileService.create(
            member.getId(),
            eventService.create(
                member.getId(),
                EventCreateRequest.builder()
                    .name("event")
                    .organizerNickname("nickname")
                    .organizerBio("bio")
                    .code("code")
                    .build()
            ),
            ProfileCreateRequest.builder()
                .nickname("participant_nickname")
                .bio("participant_bio")
                .build(),
            false
        );

        assertThat(profile).isNotNull();
        assertThat(profile).isInstanceOf(Profile.class);
    }

    @Test
    @DisplayName("프로필을 수정할 수 있어요.")
    void update() {
        Member member = memberRepository.save(
            Member.builder()
                .name("name")
                .email("meetwork@meetwork.kr")
                .build()
        );

        Profile profile = profileService.create(
            member.getId(),
            eventService.create(
                member.getId(),
                EventCreateRequest.builder()
                    .name("event")
                    .organizerNickname("nickname")
                    .organizerBio("bio")
                    .code("code")
                    .build()
            ),
            ProfileCreateRequest.builder()
                .nickname("participant_nickname")
                .bio("participant_bio")
                .build(),
            false
        );

        profileService.update(
            member.getId(),
            ProfileUpdateRequest.builder()
                .profileId(profile.getId())
                .nickname("new nickname")
                .bio("new bio")
                .isProfileImageDeleted(false)
                .build()
        );

        assertThat(profile.getNickname()).isEqualTo("new_nickname");
        assertThat(profile.getBio()).isEqualTo("new bio");
    }
}
