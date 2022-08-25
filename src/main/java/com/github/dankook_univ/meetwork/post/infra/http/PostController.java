package com.github.dankook_univ.meetwork.post.infra.http;

import com.github.dankook_univ.meetwork.post.application.PostServiceImpl;
import com.github.dankook_univ.meetwork.post.domain.Post;
import com.github.dankook_univ.meetwork.post.infra.http.request.PostCreateRequest;
import com.github.dankook_univ.meetwork.post.infra.http.request.PostUpdateRequest;
import com.github.dankook_univ.meetwork.post.infra.http.response.PostResponse;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostServiceImpl postService;

    @PostMapping("/new")
    public ResponseEntity<PostResponse> create(
        @ApiIgnore Authentication authentication,
        @RequestBody @Valid PostCreateRequest request
    ) {
        return ResponseEntity.ok().body(
            postService.create(authentication.getName(), request).toResponse()
        );
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> get(
        @ApiIgnore Authentication authentication,
        @PathVariable("postId") @Valid @NotBlank String postId
    ) {
        return ResponseEntity.ok().body(
            postService.get(authentication.getName(), postId).toResponse()
        );
    }

    @GetMapping("/list/{boardId}")
    public ResponseEntity<List<PostResponse>> getList(
        @ApiIgnore Authentication authentication,
        @PathVariable("boardId") @Valid @NotBlank String boardId,
        @RequestParam(value = "page", required = false, defaultValue = "1") int page
    ) {
        return ResponseEntity.ok().body(
            postService.getList(authentication.getName(), boardId, page)
                .stream().map(Post::toResponse)
                .collect(Collectors.toList())
        );
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponse> update(
        @ApiIgnore Authentication authentication,
        @PathVariable("postId") @Valid @NotBlank String postId,
        @RequestBody @Valid PostUpdateRequest request
    ) {
        return ResponseEntity.ok().body(
            postService.update(authentication.getName(), postId, request).toResponse()
        );
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Boolean> delete(
        @ApiIgnore Authentication authentication,
        @PathVariable("postId") @Valid @NotBlank String postId
    ) {
        postService.delete(authentication.getName(), postId);
        return ResponseEntity.ok().body(true);
    }
}
