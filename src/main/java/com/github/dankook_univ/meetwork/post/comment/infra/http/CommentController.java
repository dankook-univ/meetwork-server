package com.github.dankook_univ.meetwork.post.comment.infra.http;

import com.github.dankook_univ.meetwork.post.comment.application.CommentServiceImpl;
import com.github.dankook_univ.meetwork.post.comment.infra.http.request.CommentCreateRequest;
import com.github.dankook_univ.meetwork.post.comment.infra.http.request.CommentUpdateRequest;
import com.github.dankook_univ.meetwork.post.comment.infra.http.response.CommentResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;

    @PostMapping("/new")
    public ResponseEntity<CommentResponse> create(
        @ApiIgnore Authentication authentication,
        @RequestBody @Valid CommentCreateRequest request
    ) {
        return ResponseEntity.ok().body(
            commentService.create(authentication.getName(), request).toResponse()
        );
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponse> update(
        @ApiIgnore Authentication authentication,
        @PathVariable("commentId") @NotBlank String commentId,
        @RequestBody @Valid CommentUpdateRequest request
    ) {
        return ResponseEntity.ok().body(
            commentService.update(authentication.getName(), commentId, request).toResponse()
        );
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Boolean> delete(
        @ApiIgnore Authentication authentication,
        @PathVariable("commentId") @Valid @NotBlank String commentId
    ) {
        commentService.delete(authentication.getName(), commentId);
        return ResponseEntity.ok().body(true);
    }
}
