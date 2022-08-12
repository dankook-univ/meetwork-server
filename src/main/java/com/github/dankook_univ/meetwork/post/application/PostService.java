package com.github.dankook_univ.meetwork.post.application;

import com.github.dankook_univ.meetwork.post.domain.Post;
import com.github.dankook_univ.meetwork.post.infra.http.request.PostCreateRequest;
import java.util.List;

public interface PostService {

    Post create(String memberId, PostCreateRequest request);

    Post get(String memberId, String postId);

    List<Post> getList(String memberId, String boardId, int page);
}