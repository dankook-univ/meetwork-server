package com.github.dankook_univ.meetwork.post.application;

import com.github.dankook_univ.meetwork.post.domain.Post;
import com.github.dankook_univ.meetwork.post.infra.http.request.PostCreateRequest;
import com.github.dankook_univ.meetwork.post.infra.http.request.PostUpdateRequest;
import java.util.List;

public interface PostService {

    Post create(Long memberId, PostCreateRequest request);

    Post update(Long memberId, Long postId, PostUpdateRequest request);

    Post get(Long memberId, Long postId);

    List<Post> getList(Long memberId, Long boardId, int page);

    void delete(Long memberId, Long postId);
}