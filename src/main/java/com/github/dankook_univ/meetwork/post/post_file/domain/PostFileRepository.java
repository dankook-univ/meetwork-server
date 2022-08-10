package com.github.dankook_univ.meetwork.post.post_file.domain;

import java.util.List;
import java.util.UUID;

public interface PostFileRepository {

    PostFile save(PostFile postFile);

    List<PostFile> getByPostId(UUID postId);

    void delete(PostFile postFile);
}
