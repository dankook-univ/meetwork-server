package com.github.dankook_univ.meetwork.event.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.event.exceptions.NotFoundEventException;
import com.github.dankook_univ.meetwork.event.exceptions.NotFoundEventPermissionException;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventCreateRequest;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventUpdateRequest;
import com.github.dankook_univ.meetwork.event.infra.http.request.ProfileReleaseRequest;
import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.member.infra.persistence.MemberRepositoryImpl;
import com.github.dankook_univ.meetwork.profile.application.ProfileServiceImpl;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.exceptions.NotFoundProfileException;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class EventServiceImplTest {

    @Autowired
    EventServiceImpl eventService;

    @Autowired
    ProfileServiceImpl profileService;

    @Autowired
    MemberRepositoryImpl memberRepository;

    private Member createMember(String name, String email) {
        return memberRepository.save(
            Member.builder()
                .name(name)
                .email(email)
                .build()
        );
    }

    @Test
    @DisplayName("이벤트를 생성할 수 있어요.")
    public void create() {
        Member member = createMember("name", "meetwork@meetwork.kr");

        Event event = eventService.create(
            member.getId().toString(),
            EventCreateRequest.builder()
                .name("event")
                .organizerNickname("nickname")
                .organizerBio("bio")
                .code("code")
                .build()
        );

        assertThat(event).isNotNull();
        assertThat(event).isInstanceOf(Event.class);

        assertThat(event.getOrganizer()).isNotNull();
        assertThat(event.getOrganizer()).isInstanceOf(Profile.class);
        assertThat(event.getOrganizer().getMember().getId()).isEqualTo(member.getId());
        assertThat(event.getOrganizer().getIsAdmin()).isTrue();
    }

    @Test
    @DisplayName("이벤트에 참여할 수 있어요.")
    public void join() {
        Event event = eventService.create(
            createMember("name", "meetwork@meetwork.kr").getId().toString(),
            EventCreateRequest.builder()
                .name("event")
                .organizerNickname("nickname")
                .organizerBio("bio")
                .code("code")
                .build()
        );

        Member member = createMember("participant_name", "participant@meetwork.kr");

        Event joinedEvent = eventService.join(
            member.getId().toString(),
            event.getId().toString(),
            ProfileCreateRequest.builder()
                .nickname("participant_nickname")
                .bio("participant")
                .build(),
            false
        );

        Profile profile = profileService.get(member.getId().toString(), event.getId().toString());

        assertThat(joinedEvent).isNotNull();
        assertThat(joinedEvent).isInstanceOf(Event.class);
        assertThat(joinedEvent.getId()).isEqualTo(event.getId());

        assertThat(event.getOrganizer().getId()).isNotEqualTo(member.getId());

        assertThat(profile).isNotNull();
        assertThat(profile).isInstanceOf(Profile.class);
        assertThat(profile.getMember().getId()).isEqualTo(member.getId());
        assertThat(profile.getEvent().getId()).isEqualTo(event.getId());
    }

    @Test
    @DisplayName("이벤트를 조회할 수 있어요.")
    public void getEvent() {
        Member member = createMember("name", "meetwork@meetwork.kr");

        Event event = eventService.create(
            member.getId().toString(),
            EventCreateRequest.builder()
                .name("event")
                .organizerNickname("nickname")
                .organizerBio("bio")
                .code("code")
                .build()
        );

        Event findEvent = eventService.get(member.getId().toString(), event.getId().toString());

        assertThat(findEvent).isNotNull();
        assertThat(findEvent).isInstanceOf(Event.class);

        assertThat(findEvent.getId()).isEqualTo(event.getId());
    }

    @Test
    @DisplayName("내가 참여한 이벤트 목록을 가져올 수 있어요.")
    public void getList() {
        Event eventToBeJoined = eventService.create(
            createMember("name", "meetwork@meetwork.kr").getId().toString(),
            EventCreateRequest.builder()
                .name("event")
                .organizerNickname("nickname")
                .organizerBio("bio")
                .code("code")
                .build()
        );

        Member member = createMember("participant_name", "participant@meetwork.kr");

        Event joinedEvent = eventService.join(
            member.getId().toString(),
            eventToBeJoined.getId().toString(),
            ProfileCreateRequest.builder()
                .nickname("participant_nickname")
                .bio("participant")
                .build(),
            false
        );

        Event createdEvent = eventService.create(
            member.getId().toString(),
            EventCreateRequest.builder()
                .name("event")
                .organizerNickname("nickname")
                .organizerBio("bio")
                .code("code2")
                .build()
        );

        List<Event> list = eventService.getList(member.getId().toString(), 1);

        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0)).isInstanceOf(Event.class);
        assertThat(list.get(0).getCode()).contains("code");

        assertThat(joinedEvent.getOrganizer().getMember()).isNotEqualTo(member);
    }

    @Test
    @DisplayName("참여한 이벤트의 참여자 목록을 조회할 수 있어요.")
    public void getMemberList() {
        Event event = eventService.create(
            createMember("name", "meetwork@meetwork.kr").getId().toString(),
            EventCreateRequest.builder()
                .name("event")
                .organizerNickname("nickname")
                .organizerBio("bio")
                .code("code")
                .build()
        );

        Member member = createMember("participant_name", "participant@meetwork.kr");
        eventService.join(
            member.getId().toString(),
            event.getId().toString(),
            ProfileCreateRequest.builder()
                .nickname("participant_nickname1")
                .bio("participant")
                .build(),
            false
        );

        Member member2 = createMember("participant_name", "participant@meetwork.kr");
        eventService.join(
            member2.getId().toString(),
            event.getId().toString(),
            ProfileCreateRequest.builder()
                .nickname("participant_nickname2")
                .bio("participant")
                .build(),
            false
        );

        List<Profile> profileList = eventService.getMemberList(
            member.getId().toString(),
            event.getId().toString(),
            1);

        assertThat(event).isNotNull();
        assertThat(event).isInstanceOf(Event.class);

        assertThat(profileList).isNotNull();
        assertThat(profileList.get(0)).isInstanceOf(Profile.class);
        assertThat(profileList.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("이벤트를 수정할 수 있어요.")
    public void update() {
        Member member = createMember("name", "meetwork@meetwork.kr");

        Event event = eventService.create(
            member.getId().toString(),
            EventCreateRequest.builder()
                .name("event")
                .organizerNickname("nickname")
                .organizerBio("bio")
                .code("code")
                .build()
        );

        Event updatedEvent = eventService.update(
            member.getId().toString(),
            event.getId().toString(),
            EventUpdateRequest.builder()
                .name("new_name")
                .code("new_code")
                .meetingUrl("meeting_url")
                .build()
        );

        assertThat(updatedEvent).isNotNull();
        assertThat(updatedEvent).isInstanceOf(Event.class);

        assertThat(updatedEvent.getName()).isEqualTo("new_name");
        assertThat(updatedEvent.getCode()).isEqualTo("new_code");
        assertThat(updatedEvent.getMeetingUrl()).isEqualTo("meeting_url");
    }

    @Test
    @DisplayName("이벤트를 수정에 실패해요.")
    public void failedUpdate() {
        Event event = eventService.create(
            createMember("name", "meetwork@meetwork.kr").getId().toString(),
            EventCreateRequest.builder()
                .name("event")
                .organizerNickname("nickname")
                .organizerBio("bio")
                .code("code")
                .build()
        );

        Member member = createMember("participant_name", "participant@meetwork.kr");

        eventService.join(
            member.getId().toString(),
            event.getId().toString(),
            ProfileCreateRequest.builder()
                .nickname("participant_nickname")
                .bio("participant")
                .build(),
            false
        );

        Assertions.assertThrows(NotFoundEventPermissionException.class, () -> {
            eventService.update(
                member.getId().toString(),
                event.getId().toString(),
                EventUpdateRequest.builder()
                    .name("new_name")
                    .code("new_code")
                    .meetingUrl("meeting_url")
                    .build()
            );
        });
    }

    @Test
    @DisplayName("이벤트 코드 중복을 확인할 수 있어요.")
    public void checkExistingCode() {
        String EVENT_CODE = "code";
        String MEANINGLESS_EVENT_CODE = "event_code";

        Event event = eventService.create(
            createMember("name", "meetwork@meetwork.kr").getId().toString(),
            EventCreateRequest.builder()
                .name("event")
                .organizerNickname("nickname")
                .organizerBio("bio")
                .code(EVENT_CODE)
                .build()
        );

        Boolean expectedTrue = eventService.checkExistingCode(EVENT_CODE);
        Boolean expectedFalse = eventService.checkExistingCode(MEANINGLESS_EVENT_CODE);

        assertThat(expectedTrue).isTrue();
        assertThat(expectedFalse).isFalse();
    }

    @Test
    @DisplayName("이벤트 코드로 참여할 수 있어요.")
    public void codeJoin() {
        String EVENT_CODE = "code";

        Event event = eventService.create(
            createMember("name", "meetwork@meetwork.kr").getId().toString(),
            EventCreateRequest.builder()
                .name("event")
                .organizerNickname("nickname")
                .organizerBio("bio")
                .code(EVENT_CODE)
                .build()
        );

        Member member = createMember("participant_name", "participant@meetwork.kr");

        Event joinedEvent = eventService.codeJoin(
            member.getId().toString(),
            EVENT_CODE,
            ProfileCreateRequest.builder()
                .nickname("joinedMember")
                .bio("bio")
                .build()
        );

        Profile profile = profileService.get(member.getId().toString(), event.getId().toString());

        assertThat(joinedEvent).isNotNull();
        assertThat(joinedEvent).isInstanceOf(Event.class);
        assertThat(joinedEvent.getId()).isEqualTo(event.getId());

        assertThat(event.getOrganizer().getId()).isNotEqualTo(member.getId());

        assertThat(profile).isNotNull();
        assertThat(profile).isInstanceOf(Profile.class);
        assertThat(profile.getMember().getId()).isEqualTo(member.getId());
        assertThat(profile.getEvent().getId()).isEqualTo(event.getId());

    }

    @Test
    @DisplayName("이벤트 코드로 참여에 실패해요.")
    public void failedCodeJoin() {
        String EVENT_CODE = "code";
        String MEANINGLESS_EVENT_CODE = "event_code";

        Event event = eventService.create(
            createMember("name", "meetwork@meetwork.kr").getId().toString(),
            EventCreateRequest.builder()
                .name("event")
                .organizerNickname("nickname")
                .organizerBio("bio")
                .code(EVENT_CODE)
                .build()
        );

        Member member = createMember("participant_name", "participant@meetwork.kr");

        Assertions.assertThrows(NotFoundEventException.class, () -> {
            Event joinedEvent = eventService.codeJoin(
                member.getId().toString(),
                MEANINGLESS_EVENT_CODE,
                ProfileCreateRequest.builder()
                    .nickname("joinedMember")
                    .bio("bio")
                    .build()
            );
        });
    }

    @Test
    @DisplayName("참여한 이벤트의 내 프로필을 가져올 수 있어요.")
    public void getMyProfile() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = eventService.create(
            member.getId().toString(),
            EventCreateRequest.builder()
                .name("event")
                .organizerNickname("nickname")
                .organizerBio("bio")
                .code("code")
                .build()
        );

        Profile profile = eventService.getMyProfile(
            member.getId().toString(),
            event.getId().toString()
        );

        assertThat(profile).isNotNull();
        assertThat(profile).isInstanceOf(Profile.class);

        assertThat(profile.getMember().getId()).isEqualTo(member.getId());
        assertThat(profile.getNickname()).isEqualTo("nickname");
    }

    @Test
    @DisplayName("이벤트에서 나갈 수 있어요.")
    public void secession() {
        Member organizer = createMember("name", "meetwork@meetwork.kr");
        Event event = eventService.create(
            organizer.getId().toString(),
            EventCreateRequest.builder()
                .name("event")
                .organizerNickname("nickname")
                .organizerBio("bio")
                .code("code")
                .build()
        );

        Member participant = createMember("participant_name", "participant@meetwork.kr");
        Event joinedEvent = eventService.join(
            participant.getId().toString(),
            event.getId().toString(),
            ProfileCreateRequest.builder()
                .nickname("participant_nickname")
                .bio("participant")
                .build(),
            false
        );
        List<Profile> joinedList = eventService.getMemberList(
            organizer.getId().toString(),
            event.getId().toString(),
            1);

        eventService.secession(participant.getId().toString(), event.getId().toString());
        List<Profile> secessionList = eventService.getMemberList(
            organizer.getId().toString(),
            event.getId().toString(),
            1);

        assertThat(joinedList.size()).isEqualTo(2);
        assertThat(secessionList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("관리자는 이벤트에서 참여자를 방출시킬 수 있어요.")
    public void release() {
        Member organizer = createMember("name", "meetwork@meetwork.kr");
        Event event = eventService.create(
            organizer.getId().toString(),
            EventCreateRequest.builder()
                .name("event")
                .organizerNickname("nickname")
                .organizerBio("bio")
                .code("code")
                .build()
        );

        Member participant = createMember("participant_name", "participant@meetwork.kr");
        Event joinedEvent = eventService.join(
            participant.getId().toString(),
            event.getId().toString(),
            ProfileCreateRequest.builder()
                .nickname("participant_nickname")
                .bio("participant")
                .build()
            , true
        );
        List<Profile> joinedList = eventService.getMemberList(
            organizer.getId().toString(),
            event.getId().toString(),
            1);

        eventService.release(
            organizer.getId().toString(),
            ProfileReleaseRequest.builder()
                .profileId(participant.getId().toString())
                .eventId(event.getId().toString())
                .build());

        List<Profile> secessionList = eventService.getMemberList(
            organizer.getId().toString(),
            event.getId().toString(),
            1);

        assertThat(joinedList.size()).isEqualTo(2);
        assertThat(secessionList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("이벤트를 삭제할 수 있어요.")
    public void delete() {
        Member member = createMember("name", "meetwork@meetwork.kr");

        Event event = eventService.create(
            member.getId().toString(),
            EventCreateRequest.builder()
                .name("event")
                .organizerNickname("nickname")
                .organizerBio("bio")
                .code("code")
                .build()
        );

        assertThat(event).isNotNull();

        eventService.delete(member.getId().toString(), event.getId().toString());

        Assertions.assertThrows(NotFoundProfileException.class, () -> {
            eventService.get(member.getId().toString(), event.getId().toString());
        });
    }

    @Test
    @DisplayName("이벤트를 삭제에 실패해요.")
    public void failed_delete() {
        Event event = eventService.create(
            createMember("name", "meetwork@meetwork.kr").getId().toString(),
            EventCreateRequest.builder()
                .name("event")
                .organizerNickname("nickname")
                .organizerBio("bio")
                .code("code")
                .build()
        );

        Member member = createMember("participant_name", "participant@meetwork.kr");

        eventService.join(
            member.getId().toString(),
            event.getId().toString(),
            ProfileCreateRequest.builder()
                .nickname("participant_nickname")
                .bio("participant")
                .build(),
            false
        );

        Assertions.assertThrows(NotFoundEventPermissionException.class, () -> {
            eventService.delete(
                member.getId().toString(),
                event.getId().toString()
            );
        });
    }
}