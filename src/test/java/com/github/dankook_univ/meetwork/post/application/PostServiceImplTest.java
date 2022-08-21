package com.github.dankook_univ.meetwork.post.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.dankook_univ.meetwork.board.application.BoardServiceImpl;
import com.github.dankook_univ.meetwork.board.domain.Board;
import com.github.dankook_univ.meetwork.board.exceptions.NotFoundBoardPermissionException;
import com.github.dankook_univ.meetwork.board.infra.http.request.BoardCreateRequest;
import com.github.dankook_univ.meetwork.event.application.EventServiceImpl;
import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventCreateRequest;
import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.member.infra.persistence.MemberRepositoryImpl;
import com.github.dankook_univ.meetwork.post.domain.Post;
import com.github.dankook_univ.meetwork.post.exceptions.NotFoundPostException;
import com.github.dankook_univ.meetwork.post.infra.http.request.PostCreateRequest;
import com.github.dankook_univ.meetwork.post.infra.http.request.PostUpdateRequest;
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
public class PostServiceImplTest {

    @Autowired
    PostServiceImpl postService;

    @Autowired
    MemberRepositoryImpl memberRepository;

    @Autowired
    EventServiceImpl eventService;

    @Autowired
    BoardServiceImpl boardService;

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
            member.getId().toString(),
            EventCreateRequest.builder()
                .name("eventName")
                .organizerNickname("nickname")
                .organizerBio("bio")
                .code("code")
                .build()
        );
    }

    private Board createBoard(Member member, Event event, String name, Boolean isAdmin) {
        return boardService.create(
            member.getId().toString(),
            BoardCreateRequest.builder()
                .name(name)
                .adminOnly(isAdmin)
                .eventId(event.getId().toString())
                .build());
    }

    @Test
    @DisplayName("게시물을 생성할 수 있어요.")
    public void create() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);
        Board adminOnlyBoard = createBoard(member, event, "소통방", true);

        Post post = postService.create(
            member.getId().toString(),
            PostCreateRequest.builder()
                .title("title")
                .content("content")
                .boardId(adminOnlyBoard.getId().toString())
                .build()
        );

        assertThat(post).isNotNull();
        assertThat(post).isInstanceOf(Post.class);

        assertThat(post.getTitle()).isEqualTo("title");
        assertThat(post.getWriter().getMember().getId()).isEqualTo(member.getId());
        assertThat(post.getBoard().getId()).isEqualTo(adminOnlyBoard.getId());

        assertThat(adminOnlyBoard.getAdminOnly()).isTrue();
    }

    @Test
    @DisplayName("게시판 권한이 없으면 게시물 생성에 실패해요.")
    public void failedCreate() {
        Member organizer = createMember("organizer", "meetwork@meetwork.kr");
        Event event = createEvent(organizer);
        Board adminOnlyBoard = createBoard(organizer, event, "소통방", true);

        Member participant = createMember("participant", "meetwork@meetwork.kr");
        Event joinedEvent = eventService.join(
            participant.getId().toString(),
            event.getId().toString(),
            ProfileCreateRequest.builder()
                .nickname("participant_nickname")
                .bio("participant")
                .build(),
            false
        );

        Assertions.assertThrows(NotFoundBoardPermissionException.class, () -> {
            Post post = postService.create(
                participant.getId().toString(),
                PostCreateRequest.builder()
                    .title("title")
                    .content("content")
                    .boardId(adminOnlyBoard.getId().toString())
                    .build()
            );
        });
    }

    @Test
    @DisplayName("게시물 수정을 할 수 있어요.")
    public void update() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);
        Board adminOnlyBoard = createBoard(member, event, "소통방", true);

        Post post = postService.create(
            member.getId().toString(),
            PostCreateRequest.builder()
                .title("title")
                .content("content")
                .boardId(adminOnlyBoard.getId().toString())
                .build()
        );

        Post updatedPost = postService.update(
            member.getId().toString(),
            post.getId().toString(),
            PostUpdateRequest.builder()
                .title("updatedPost")
                .build()
        );

        assertThat(adminOnlyBoard.getAdminOnly()).isTrue();
        assertThat(post).isNotNull();
        assertThat(post).isInstanceOf(Post.class);

        assertThat(updatedPost).isNotNull();
        assertThat(updatedPost).isInstanceOf(Post.class);
        assertThat(updatedPost.getTitle()).isEqualTo("updatedPost");
    }

    @Test
    @DisplayName("게시물을 가져올 수 있어요.")
    public void get() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);
        Board adminOnlyBoard = createBoard(member, event, "소통방", true);

        Post post = postService.create(
            member.getId().toString(),
            PostCreateRequest.builder()
                .title("title")
                .content("content")
                .boardId(adminOnlyBoard.getId().toString())
                .build()
        );

        Post finedPost = postService.get(member.getId().toString(), post.getId().toString());

        assertThat(post).isNotNull();
        assertThat(post).isInstanceOf(Post.class);

        assertThat(finedPost).isNotNull();
        assertThat(finedPost).isInstanceOf(Post.class);

        assertThat(finedPost.getId()).isEqualTo(post.getId());
        assertThat(finedPost.getTitle()).isEqualTo("title");
        assertThat(finedPost.getContent()).isEqualTo("content");
    }

    @Test
    @DisplayName("게시물 목록을 가져올 수 있어요.")
    public void getList() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);
        Board adminOnlyBoard = createBoard(member, event, "소통방", true);

        Post firstPost = postService.create(
            member.getId().toString(),
            PostCreateRequest.builder()
                .title("title")
                .content("content")
                .boardId(adminOnlyBoard.getId().toString())
                .build()
        );
        Post secondPost = postService.create(
            member.getId().toString(),
            PostCreateRequest.builder()
                .title("title")
                .content("content")
                .boardId(adminOnlyBoard.getId().toString())
                .build()
        );

        List<Post> list = postService.getList(
            member.getId().toString(),
            adminOnlyBoard.getId().toString(),
            1);

        assertThat(adminOnlyBoard).isNotNull();
        assertThat(adminOnlyBoard.getAdminOnly()).isTrue();
        assertThat(firstPost).isNotNull();
        assertThat(secondPost).isNotNull();

        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("작성자는 본인의 게시물을 삭제할 수 있어요.")
    public void delete() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);
        Board adminOnlyBoard = createBoard(member, event, "소통방", true);

        Post post = postService.create(
            member.getId().toString(),
            PostCreateRequest.builder()
                .title("title")
                .content("content")
                .boardId(adminOnlyBoard.getId().toString())
                .build()
        );

        postService.delete(member.getId().toString(), post.getId().toString());

        assertThat(post).isNotNull();
        Assertions.assertThrows(NotFoundPostException.class, () -> {
            postService.get(member.getId().toString(), post.getId().toString());
        });
    }

    @Test
    @DisplayName("관리자는 어느 게시물이든 삭제할 수 있어요.")
    public void deleteWithAdmin() {
        Member organizer = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(organizer);
        Board adminOnlyBoard = createBoard(organizer, event, "소통방", false);

        Member participant = createMember("participant", "meetwork@meetwork.kr");
        eventService.join(
            participant.getId().toString(),
            event.getId().toString(),
            ProfileCreateRequest.builder()
                .nickname("participant_nickname")
                .bio("participant")
                .build(),
            false
        );

        Post post = postService.create(
            participant.getId().toString(),
            PostCreateRequest.builder()
                .title("title")
                .content("content")
                .boardId(adminOnlyBoard.getId().toString())
                .build()
        );

        postService.delete(organizer.getId().toString(), post.getId().toString());

        assertThat(post).isNotNull();
        Assertions.assertThrows(NotFoundPostException.class, () -> {
            postService.get(organizer.getId().toString(), post.getId().toString());
        });
    }

}
