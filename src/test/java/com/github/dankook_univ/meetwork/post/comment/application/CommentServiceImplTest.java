package com.github.dankook_univ.meetwork.post.comment.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.dankook_univ.meetwork.board.application.BoardServiceImpl;
import com.github.dankook_univ.meetwork.board.domain.Board;
import com.github.dankook_univ.meetwork.board.infra.http.request.BoardCreateRequest;
import com.github.dankook_univ.meetwork.event.application.EventServiceImpl;
import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventCreateRequest;
import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.member.infra.persistence.MemberRepositoryImpl;
import com.github.dankook_univ.meetwork.post.application.PostServiceImpl;
import com.github.dankook_univ.meetwork.post.comment.domain.Comment;
import com.github.dankook_univ.meetwork.post.comment.infra.http.request.CommentCreateRequest;
import com.github.dankook_univ.meetwork.post.comment.infra.http.request.CommentUpdateRequest;
import com.github.dankook_univ.meetwork.post.domain.Post;
import com.github.dankook_univ.meetwork.post.infra.http.request.PostCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CommentServiceImplTest {

    @Autowired
    CommentServiceImpl commentService;

    @Autowired
    MemberRepositoryImpl memberRepository;

    @Autowired
    EventServiceImpl eventService;

    @Autowired
    BoardServiceImpl boardService;

    @Autowired
    PostServiceImpl postService;

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
    @DisplayName("댓글을 생성할 수 있어요.")
    public void create() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);
        Board adminOnlyBoard = createBoard(member, event, "소통방", true);

        Post post = postService.create(
            member.getId().toString(),
            PostCreateRequest.builder()
                .content("content")
                .boardId(adminOnlyBoard.getId().toString())
                .build()
        );

        Comment comment = commentService.create(
            member.getId().toString(),
            CommentCreateRequest.builder()
                .content("comment")
                .postId(post.getId().toString())
                .build()
        );

        assertThat(comment).isNotNull().isInstanceOf(Comment.class);
        assertThat(comment.getContent()).isEqualTo("comment");

        assertThat(post.getComments().size()).isEqualTo(1);
        assertThat(post.getComments().get(0)).isEqualTo(comment);
        assertThat(post.getComments().get(0).getContent()).isEqualTo("comment");
    }

    @Test
    @DisplayName("댓글을 수정할 수 있어요.")
    public void update() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);
        Board adminOnlyBoard = createBoard(member, event, "소통방", true);

        Post post = postService.create(
            member.getId().toString(),
            PostCreateRequest.builder()
                .content("content")
                .boardId(adminOnlyBoard.getId().toString())
                .build()
        );

        Comment comment = commentService.create(
            member.getId().toString(),
            CommentCreateRequest.builder()
                .content("comment")
                .postId(post.getId().toString())
                .build()
        );

        commentService.update(
            member.getId().toString(),
            comment.getId().toString(),
            CommentUpdateRequest.builder()
                .content("updated comment")
                .build()
        );

        assertThat(comment).isNotNull().isInstanceOf(Comment.class);
        assertThat(comment.getContent()).isEqualTo("updated comment");
        assertThat(post.getComments().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("댓글을 삭제할 수 있어요.")
    public void delete() {
        Member member = createMember("name", "meetwork@meetwork.kr");
        Event event = createEvent(member);
        Board adminOnlyBoard = createBoard(member, event, "소통방", true);

        Post post = postService.create(
            member.getId().toString(),
            PostCreateRequest.builder()
                .content("content")
                .boardId(adminOnlyBoard.getId().toString())
                .build()
        );

        Comment comment = commentService.create(
            member.getId().toString(),
            CommentCreateRequest.builder()
                .content("comment")
                .postId(post.getId().toString())
                .build()
        );

        assertThat(comment).isNotNull().isInstanceOf(Comment.class);
        assertThat(post.getComments().size()).isEqualTo(1);

        commentService.delete(member.getId().toString(), comment.getId().toString());

        assertThat(post.getComments().size()).isEqualTo(0);
    }
}
