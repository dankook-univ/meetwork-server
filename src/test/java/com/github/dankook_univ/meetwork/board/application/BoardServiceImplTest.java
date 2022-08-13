package com.github.dankook_univ.meetwork.board.application;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.dankook_univ.meetwork.board.domain.Board;
import com.github.dankook_univ.meetwork.board.exceptions.NotFoundBoardException;
import com.github.dankook_univ.meetwork.board.exceptions.NotFoundBoardPermissionException;
import com.github.dankook_univ.meetwork.board.infra.http.request.BoardCreateRequest;
import com.github.dankook_univ.meetwork.board.infra.http.request.BoardUpdateRequest;
import com.github.dankook_univ.meetwork.event.application.EventServiceImpl;
import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventCreateRequest;
import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.member.infra.persistence.MemberRepositoryImpl;
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
public class BoardServiceImplTest {

    @Autowired
    BoardServiceImpl boardService;

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

    private Event createEvent(Member member, String name) {
        return eventService.create(
            member.getId().toString(),
            EventCreateRequest.builder()
                .name(name)
                .organizerNickname("nickname")
                .organizerBio("bio")
                .code("code")
                .build()
        );
    }

    @Test
    @DisplayName("게시판을 생성할 수 있어요.")
    public void create() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member, "event");

        Board board = boardService.create(
            member.getId().toString(),
            BoardCreateRequest.builder()
                .name("소통방")
                .adminOnly(true)
                .eventId(event.getId().toString())
                .build());

        assertThat(board).isNotNull();
        assertThat(board).isInstanceOf(Board.class);

        assertThat(board.getName()).isEqualTo("소통방");
        assertThat(board.getAdminOnly()).isTrue();
        assertThat(board.getEvent()).isInstanceOf(Event.class);
    }

    @Test
    @DisplayName("게시판을 수정할 수 있어요.")
    public void update() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member, "event");

        Board board = boardService.create(
            member.getId().toString(),
            BoardCreateRequest.builder()
                .name("소통방")
                .adminOnly(true)
                .eventId(event.getId().toString())
                .build()
        );

        Board updatedBoard = boardService.update(
            member.getId().toString(),
            board.getId().toString(),
            BoardUpdateRequest.builder()
                .name("사진방")
                .adminOnly(false)
                .build()
        );

        assertThat(updatedBoard).isNotNull();
        assertThat(updatedBoard).isInstanceOf(Board.class);

        assertThat(updatedBoard.getName()).isEqualTo("사진방");
        assertThat(updatedBoard.getAdminOnly()).isFalse();
    }

    @Test
    @DisplayName("게시판을 수정할 수 없어요.")
    public void failedUpdate() {
        Member organizer = createMember("organizer", "meetwork@meetwork.kr");
        Member participant = createMember("participant", "meetwork@meetwork.kr");

        Event event = createEvent(organizer, "event");
        eventService.join(
            participant.getId().toString(),
            event.getId().toString(),
            ProfileCreateRequest.builder()
                .nickname("participant_nickname")
                .bio("participant")
                .build()
        );

        Board board = boardService.create(
            organizer.getId().toString(),
            BoardCreateRequest.builder()
                .name("소통방")
                .adminOnly(true)
                .eventId(event.getId().toString())
                .build()
        );

        Assertions.assertThrows(NotFoundBoardPermissionException.class, () -> {
            boardService.update(
                participant.getId().toString(),
                board.getId().toString(),
                BoardUpdateRequest.builder()
                    .name("사진방")
                    .adminOnly(false)
                    .build()
            );
        });
    }

    @Test
    @DisplayName("게시판 목록을 가져올 수 있어요.")
    public void getList() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member, "event");

        List<Board> list = boardService.getList(
            member.getId().toString(),
            event.getId().toString()
        );

        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(3);

        assertThat(list.get(0).getName()).isEqualTo("공지 게시판");
        assertThat(list.get(1).getName()).isEqualTo("Q&A 게시판");
        assertThat(list.get(2).getName()).isEqualTo("자유 게시판");
    }

    @Test
    @DisplayName("게시판을 삭제할 수 있어요.")
    public void delete() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member, "event");

        Board board = boardService.create(
            member.getId().toString(),
            BoardCreateRequest.builder()
                .name("소통방")
                .adminOnly(true)
                .eventId(event.getId().toString())
                .build());

        assertThat(board).isNotNull();

        boardService.delete(member.getId().toString(), board.getId().toString());

        Assertions.assertThrows(NotFoundBoardException.class, () -> {
            boardService.get(
                board.getId().toString()
            );
        });
    }
}
