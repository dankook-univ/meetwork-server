package com.github.dankook_univ.meetwork.post.post_file.domain;

import java.util.List;

public interface PostFileRepository {

    PostFile save(PostFile postFile);

    List<PostFile> getByPostId(String postId);

    void delete(PostFile postFile);
}
