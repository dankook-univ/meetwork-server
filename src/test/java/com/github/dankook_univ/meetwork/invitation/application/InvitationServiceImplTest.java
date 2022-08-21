package com.github.dankook_univ.meetwork.invitation.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.dankook_univ.meetwork.event.application.EventServiceImpl;
import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventCreateRequest;
import com.github.dankook_univ.meetwork.invitation.domain.Invitation;
import com.github.dankook_univ.meetwork.invitation.infra.http.request.InvitationCreateRequest;
import com.github.dankook_univ.meetwork.invitation.infra.http.request.InvitationInformation;
import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.member.infra.persistence.MemberRepositoryImpl;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class InvitationServiceImplTest {

    @Autowired
    InvitationServiceImpl invitationService;

    @Autowired
    EventServiceImpl eventService;

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

    private Event createEvent(Member member, String name, String code) {
        return eventService.create(
            member.getId().toString(),
            EventCreateRequest.builder()
                .name(name)
                .organizerNickname("nickname")
                .organizerBio("bio")
                .code(code)
                .build()
        );
    }

    @Test
    @DisplayName("관리자는 이벤트에 초대할 수 있어요.")
    public void create() {
        Member organizer = createMember("organizer", "meetwork@meetwork.kr");
        Event event = createEvent(organizer, "event", "code");

        Member participant1 = createMember("participant1", "govl6113@meetwork.kr");
        Member participant2 = createMember("participant2", "himitery0131@meetwork.kr");

        Boolean check = invitationService.create(
            organizer.getId().toString(),
            InvitationCreateRequest.builder()
                .eventId(event.getId().toString())
                .invitationInformations(
                    List.of(
                        InvitationInformation.builder()
                            .email("govl6113@meetwork.kr")
                            .isAdmin(true)
                            .build(),
                        InvitationInformation.builder()
                            .email("himitery0131@meetwork.kr")
                            .isAdmin(false)
                            .build()
                    )
                ).build()
        );

        List<Invitation> participant1InvitationList = invitationService.getList(
            participant1.getId().toString());
        List<Invitation> participant2InvitationList = invitationService.getList(
            participant2.getId().toString());

        assertThat(check).isTrue();

        assertThat(participant1InvitationList.size()).isEqualTo(1);
        assertThat(participant1InvitationList.get(0).getIsAdmin()).isTrue();

        assertThat(participant2InvitationList.size()).isEqualTo(1);
        assertThat(participant2InvitationList.get(0).getIsAdmin()).isFalse();
    }

    @Test
    @DisplayName("초대된 이벤트 목록을 최근순으로 가져올 수 있어요.")
    public void getList() {
        Member organizer1 = createMember("organizer1", "meetwork@meetwork.kr");
        Event event1 = createEvent(organizer1, "event", "code1");

        Member organizer2 = createMember("organizer2", "meetwork@meetwork.kr");
        Event event2 = createEvent(organizer2, "event", "code2");

        Member participant = createMember("participant1", "govl6113@meetwork.kr");

        invitationService.create(
            organizer1.getId().toString(),
            InvitationCreateRequest.builder()
                .eventId(event1.getId().toString())
                .invitationInformations(
                    List.of(
                        InvitationInformation.builder()
                            .email("govl6113@meetwork.kr")
                            .isAdmin(true)
                            .build()
                    )
                ).build()
        );
        invitationService.create(
            organizer2.getId().toString(),
            InvitationCreateRequest.builder()
                .eventId(event2.getId().toString())
                .invitationInformations(
                    List.of(
                        InvitationInformation.builder()
                            .email("govl6113@meetwork.kr")
                            .isAdmin(false)
                            .build()
                    )
                ).build()
        );

        List<Invitation> list = invitationService.getList(participant.getId().toString());

        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(2);

        assertThat(list.get(0).getEvent()).isEqualTo(event2);
        assertThat(list.get(0).getIsAdmin()).isFalse();

        assertThat(list.get(1).getEvent()).isEqualTo(event1);
        assertThat(list.get(1).getIsAdmin()).isTrue();
    }

    @Test
    @DisplayName("초대 승낙으로 이벤트에 참가할 수 있어요.")
    public void join() {
        Member organizer = createMember("organizer", "meetwork@meetwork.kr");
        Event event = createEvent(organizer, "event", "code");

        Member participant = createMember("participant", "govl6113@meetwork.kr");

        invitationService.create(
            organizer.getId().toString(),
            InvitationCreateRequest.builder()
                .eventId(event.getId().toString())
                .invitationInformations(
                    List.of(
                        InvitationInformation.builder()
                            .email("govl6113@meetwork.kr")
                            .isAdmin(true)
                            .build()
                    )
                ).build()
        );

        invitationService.join(
            participant.getId().toString(),
            event.getId().toString(),
            ProfileCreateRequest.builder()
                .nickname("participant")
                .bio("bio")
                .build()
        );

        Profile profile = eventService.getMyProfile(
            participant.getId().toString(),
            event.getId().toString()
        );

        assertThat(profile).isNotNull().isInstanceOf(Profile.class);
        assertThat(profile.getMember()).isEqualTo(participant);
        assertThat(profile.getEvent()).isEqualTo(event);
        assertThat(profile.getIsAdmin()).isTrue();
    }

    @Test
    @DisplayName("초대를 거절할 수 있어요.")
    public void delete() {
        Member organizer = createMember("organizer", "meetwork@meetwork.kr");
        Event event = createEvent(organizer, "event", "code");

        Member participant = createMember("participant", "govl6113@meetwork.kr");

        invitationService.create(
            organizer.getId().toString(),
            InvitationCreateRequest.builder()
                .eventId(event.getId().toString())
                .invitationInformations(
                    List.of(
                        InvitationInformation.builder()
                            .email("govl6113@meetwork.kr")
                            .isAdmin(true)
                            .build()
                    )
                ).build()
        );

        List<Invitation> invitationList = invitationService.getList(participant.getId().toString());

        invitationService.delete(participant.getId().toString(), event.getId().toString());

        List<Invitation> refuseList = invitationService.getList(participant.getId().toString());

        assertThat(invitationList.size()).isEqualTo(1);
        assertThat(refuseList.size()).isEqualTo(0);
    }
}