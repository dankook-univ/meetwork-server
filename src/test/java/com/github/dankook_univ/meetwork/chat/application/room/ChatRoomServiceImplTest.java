package com.github.dankook_univ.meetwork.chat.application.room;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.dankook_univ.meetwork.chat.application.message.ChatMessageServiceImpl;
import com.github.dankook_univ.meetwork.chat.domain.message.ChatMessage;
import com.github.dankook_univ.meetwork.chat.domain.room.ChatRoom;
import com.github.dankook_univ.meetwork.chat.exceptions.AlreadyChatRoomNameException;
import com.github.dankook_univ.meetwork.chat.exceptions.NotFoundChatRoomException;
import com.github.dankook_univ.meetwork.chat.exceptions.NotFoundChatRoomPermissionException;
import com.github.dankook_univ.meetwork.chat.exceptions.NotParticipatedMemberException;
import com.github.dankook_univ.meetwork.chat.infra.http.request.ChatRoomCreateRequest;
import com.github.dankook_univ.meetwork.chat.infra.http.request.ChatRoomUpdateRequest;
import com.github.dankook_univ.meetwork.chat.infra.persistence.room.ChatRoomRepositoryImpl;
import com.github.dankook_univ.meetwork.event.application.EventServiceImpl;
import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventCreateRequest;
import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.member.infra.persistence.MemberRepositoryImpl;
import com.github.dankook_univ.meetwork.profile.application.ProfileServiceImpl;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ChatRoomServiceImplTest {

    @Autowired
    private ChatRoomServiceImpl chatRoomService;
    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private ProfileServiceImpl profileService;
    @Autowired
    private ChatRoomRepositoryImpl chatRoomRepository;
    @Autowired
    private MemberRepositoryImpl memberRepository;

    @Autowired
    private ChatMessageServiceImpl chatMessageService;


    private Member createMember(String name, String email) {
        return memberRepository.save(
            Member.builder()
                .name(name)
                .email(email)
                .build()
        );
    }

    private Event createEvent(Member member) {
        return eventService.create(
            member.getId(),
            EventCreateRequest.builder()
                .name("name")
                .code("code")
                .meetingCode("meetingCode")
                .organizerNickname("nickname")
                .organizerBio("bio")
                .build()
        );
    }

    @Test
    @DisplayName("채팅방을 생성할 수 있어요.")
    public void createChatRoom() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);

        ChatRoom room = chatRoomService.create(
            member.getId(),
            event.getId(),
            ChatRoomCreateRequest.builder()
                .name("name")
                .isPrivate(true)
                .build()
        );

        assertThat(room).isNotNull();
        assertThat(room).isInstanceOf(ChatRoom.class);

        assertThat(room.getParticipants().size()).isEqualTo(1);

        ChatRoom newRoom = chatRoomService.create(
            member.getId(),
            event.getId(),
            ChatRoomCreateRequest.builder()
                .name("new name")
                .isPrivate(true)
                .build()
        );

        assertThat(newRoom).isNotNull();
        assertThat(newRoom).isInstanceOf(ChatRoom.class);

        assertThat(newRoom.getParticipants().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("채팅방 생성에 실패해요. (중복 이름)")
    public void createChatRoomWithFailed() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);

        chatRoomService.create(
            member.getId(),
            event.getId(),
            ChatRoomCreateRequest.builder()
                .name("name")
                .isPrivate(true)
                .build()
        );

        Assertions.assertThrows(AlreadyChatRoomNameException.class, () -> chatRoomService.create(
            member.getId(),
            event.getId(),
            ChatRoomCreateRequest.builder()
                .name("name")
                .isPrivate(true)
                .build()
        ));

        Assertions.assertThrows(AlreadyChatRoomNameException.class, () -> chatRoomService.create(
            member.getId(),
            event.getId(),
            ChatRoomCreateRequest.builder()
                .name("name")
                .isPrivate(false)
                .build()
        ));
    }

    @Test
    @DisplayName("채팅방을 수정할 수 있어요.")
    public void updateChatRoom()
        throws NotParticipatedMemberException, NotFoundChatRoomPermissionException {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);

        ChatRoom room = chatRoomService.create(
            member.getId(),
            event.getId(),
            ChatRoomCreateRequest.builder()
                .name("name")
                .isPrivate(true)
                .build()
        );

        ChatRoom newRoom = chatRoomService.update(
            member.getId(),
            event.getId(),
            room.getId(),
            ChatRoomUpdateRequest.builder()
                .name("new name")
                .isPrivate(false)
                .build()
        );

        assertThat(newRoom).isNotNull().isInstanceOf(ChatRoom.class);

        assertThat(newRoom.getName()).isEqualTo("new name");
        assertThat(newRoom.getIsPrivate()).isEqualTo(false);
    }

    @Test
    @DisplayName("채팅방에 참여할 수 있어요.")
    public void joinChatRoom() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);

        ChatRoom room = chatRoomService.create(
            member.getId(),
            event.getId(),
            ChatRoomCreateRequest.builder()
                .name("name")
                .isPrivate(true)
                .build()
        );

        assertThat(room.getParticipants().size()).isEqualTo(1);

        Member other = createMember("other", "other@meetwork.kr");
        eventService.join(
            other.getId(),
            event.getId(),
            ProfileCreateRequest.builder()
                .nickname("other_nickname")
                .bio("other_bio")
                .build(),
            false
        );

        ChatRoom joinedRoom = chatRoomService.join(
            other.getId(),
            room.getId()
        );

        assertThat(joinedRoom).isNotNull().isInstanceOf(ChatRoom.class);
        assertThat(joinedRoom).isEqualTo(room);

        assertThat(joinedRoom.getParticipants().size()).isEqualTo(2);
    }


    @Test
    @DisplayName("채팅방을 생성과 함께 초대할 수 있어요.")
    public void createChatRoomWithInviteMembers() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);

        List<Long> memberIds = IntStream.range(0, 10).mapToObj(
            index -> createMember("name-" + index, "meetwork-" + index + "@meetwork.kr").getId()

        ).collect(Collectors.toList());

        List<Long> participantIds = IntStream.range(0, 10).peek(
                index -> eventService.join(
                    memberIds.get(index),
                    event.getId(),
                    ProfileCreateRequest.builder()
                        .nickname("nickname-" + index)
                        .bio("bio-" + index)
                        .build(),
                    false
                )
            ).mapToObj(
                (index) -> profileService.get(
                    memberIds.get(index),
                    event.getId()
                ).getId()
            )
            .collect(Collectors.toList());

        ChatRoom room = chatRoomService.create(
            member.getId(),
            event.getId(),
            ChatRoomCreateRequest.builder()
                .name("name")
                .participantIds(participantIds)
                .isPrivate(true)
                .build()
        );

        assertThat(room).isNotNull();
        assertThat(room).isInstanceOf(ChatRoom.class);

        assertThat(room.getParticipants().size()).isEqualTo(11);
    }

    @Test
    @DisplayName("채팅방을 삭제할 수 있어요.")
    public void deleteChatRoom()
        throws NotParticipatedMemberException, NotFoundChatRoomPermissionException {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Member participant = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);

        eventService.join(
            participant.getId(),
            event.getId(),
            ProfileCreateRequest.builder()
                .nickname("participant")
                .bio("bio")
                .build(),
            false
        );

        ChatRoom room = chatRoomService.create(
            member.getId(),
            event.getId(),
            ChatRoomCreateRequest.builder()
                .name("name")
                .isPrivate(true)
                .build()
        );

        chatRoomService.join(participant.getId(), room.getId());

        ChatMessage message = chatMessageService.send(
            member.getId(),
            room.getId(),
            "message"
        );

        ChatMessage message1 = chatMessageService.send(
            participant.getId(),
            room.getId(),
            "message"
        );

        assertThat(chatRoomService.delete(
            member.getId(),
            event.getId(),
            room.getId()
        )).isTrue();

        assertThat(chatRoomRepository.getById(room.getId()).isPresent()).isFalse();
    }

    @Test
    @DisplayName("공개된 모든 채팅방을 조회할 수 있어요.")
    public void getChatRooms() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);

        IntStream.range(0, 10).forEach(index -> chatRoomService.create(
            member.getId(),
            event.getId(),
            ChatRoomCreateRequest.builder()
                .name("name-" + index)
                .isPrivate(index % 2 == 0)
                .build()
        ));

        List<ChatRoom> rooms = chatRoomService.getChatRoomList(
            member.getId(),
            event.getId()
        );

        assertThat(rooms).isNotNull().isInstanceOf(List.class);
        assertThat(rooms.size()).isEqualTo(10);

        Member other = createMember("other", "other@meetwork.kr");
        eventService.join(
            other.getId(),
            event.getId(),
            ProfileCreateRequest.builder()
                .nickname("other_nickname")
                .bio("other_bio")
                .build(),
            false
        );

        rooms = chatRoomService.getChatRoomList(
            other.getId(),
            event.getId()
        );

        assertThat(rooms).isNotNull().isInstanceOf(List.class);
        assertThat(rooms.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("참여중인 채팅방을 조회할 수 있어요.")
    public void getParticipatedChatRooms() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);

        IntStream.range(0, 10).forEach(index -> chatRoomService.create(
            member.getId(),
            event.getId(),
            ChatRoomCreateRequest.builder()
                .name("name-" + index)
                .isPrivate(index % 2 == 0)
                .build()
        ));

        List<ChatRoom> rooms = chatRoomService.getParticipatedChatRoomList(
            member.getId(),
            event.getId()
        );

        assertThat(rooms).isNotNull().isInstanceOf(List.class);
        assertThat(rooms.size()).isEqualTo(10);

        Member other = createMember("other", "other@meetwork.kr");
        eventService.join(
            other.getId(),
            event.getId(),
            ProfileCreateRequest.builder()
                .nickname("other_nickname")
                .bio("other_bio")
                .build(),
            false
        );

        rooms = chatRoomService.getParticipatedChatRoomList(
            other.getId(),
            event.getId()
        );

        assertThat(rooms).isNotNull().isInstanceOf(List.class);
        assertThat(rooms.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("채팅방을 조회할 수 있어요.")
    public void getChatRoom() throws NotParticipatedMemberException {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);

        ChatRoom room = chatRoomService.create(
            member.getId(),
            event.getId(),
            ChatRoomCreateRequest.builder()
                .name("name")
                .isPrivate(true)
                .build()
        );

        ChatRoom findRoom = chatRoomService.getChatRoom(
            member.getId(),
            room.getId()
        );

        assertThat(findRoom).isNotNull().isInstanceOf(ChatRoom.class);
        assertThat(findRoom).isEqualTo(room);
    }

    @Test
    @DisplayName("채팅방 조회에 실패해요.")
    public void getChatRoomWithFailed() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);

        ChatRoom room = chatRoomService.create(
            member.getId(),
            event.getId(),
            ChatRoomCreateRequest.builder()
                .name("name")
                .isPrivate(true)
                .build()
        );

        Member other = createMember("other", "other@meetwork.kr");
        eventService.join(
            other.getId(),
            event.getId(),
            ProfileCreateRequest.builder()
                .nickname("other_nickname")
                .bio("other_bio")
                .build(),
            false
        );

        Assertions.assertThrows(NotFoundChatRoomException.class,
            () -> chatRoomService.getChatRoom(
                other.getId(),
                1000L
            ));

        Assertions.assertThrows(NotParticipatedMemberException.class,
            () -> chatRoomService.getChatRoom(
                other.getId(),
                room.getId()
            ));
    }
}