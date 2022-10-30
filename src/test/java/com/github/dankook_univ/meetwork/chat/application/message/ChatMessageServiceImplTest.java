package com.github.dankook_univ.meetwork.chat.application.message;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.dankook_univ.meetwork.chat.application.room.ChatRoomServiceImpl;
import com.github.dankook_univ.meetwork.chat.domain.message.ChatMessage;
import com.github.dankook_univ.meetwork.chat.domain.room.ChatRoom;
import com.github.dankook_univ.meetwork.chat.exceptions.NotParticipatedMemberException;
import com.github.dankook_univ.meetwork.chat.infra.http.request.ChatRoomCreateRequest;
import com.github.dankook_univ.meetwork.event.application.EventServiceImpl;
import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventCreateRequest;
import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.member.infra.persistence.MemberRepositoryImpl;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ChatMessageServiceImplTest {

    @Autowired
    private ChatMessageService chatMessageService;
    @Autowired
    private ChatRoomServiceImpl chatRoomService;
    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private MemberRepositoryImpl memberRepository;

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

    private ChatRoom createChatRoom(Member member, Event event) {
        return chatRoomService.create(
            member.getId(),
            event.getId(),
            ChatRoomCreateRequest.builder()
                .name("name")
                .isPrivate(false)
                .build()
        );
    }

    @Test
    @DisplayName("채팅방 메시지를 생성할 수 있어요.")
    public void createChatMessage() throws NotParticipatedMemberException {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);
        ChatRoom room = createChatRoom(member, event);

        ChatMessage message = chatMessageService.send(
            member.getId(),
            room.getId(),
            "message"
        );

        assertThat(message).isNotNull().isInstanceOf(ChatMessage.class);
        assertThat(message.getMessage()).isEqualTo("message");
    }

    @Test
    @DisplayName("채팅방 메시지 생성에 실패해요.")
    public void createChatMessageWithFailed() throws NotParticipatedMemberException {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);
        ChatRoom room = createChatRoom(member, event);

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

        Assertions.assertThrows(NotParticipatedMemberException.class, () -> chatMessageService.send(
            other.getId(),
            room.getId(),
            "message"
        ));

        List<ChatMessage> messages = chatMessageService.getByRoomId(
            member.getId(),
            room.getId()
        );

        assertThat(messages).isNotNull().isInstanceOf(List.class);
        assertThat(messages.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("채팅방 메시지를 조회할 수 있어요.")
    public void getChatMessages() throws NotParticipatedMemberException {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);
        ChatRoom room = createChatRoom(member, event);

        IntStream.range(0, 10).forEach((index) ->
            {
                try {
                    chatMessageService.send(
                        member.getId(),
                        room.getId(),
                        "message-" + index
                    );
                } catch (NotParticipatedMemberException ignored) {
                }
            }
        );

        List<ChatMessage> messages = chatMessageService.getByRoomId(
            member.getId(),
            room.getId()
        );

        assertThat(messages).isNotNull().isInstanceOf(List.class);
        assertThat(messages.size()).isEqualTo(10);
    }
}