package com.github.dankook_univ.meetwork.post.application;

import com.github.dankook_univ.meetwork.post.domain.Post;
import com.github.dankook_univ.meetwork.post.infra.http.request.PostCreateRequest;
import com.github.dankook_univ.meetwork.post.infra.http.request.PostUpdateRequest;
import java.util.List;

public interface PostService {

    Post create(String memberId, PostCreateRequest request);

    Post update(String memberId, String postId, PostUpdateRequest request);

    Post get(String memberId, String postId);

    List<Post> getList(String memberId, String boardId, int page);

    void delete(String memberId, String postId);
}
