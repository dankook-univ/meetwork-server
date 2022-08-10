package com.github.dankook_univ.meetwork.post.post_file.infra.persistence;

import com.github.dankook_univ.meetwork.post.post_file.domain.PostFile;
import com.github.dankook_univ.meetwork.post.post_file.domain.PostFileRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostFileRepositoryImpl implements PostFileRepository {

    private final PostFileJpaRepository postFileRepository;

    @Override
    public PostFile save(PostFile postFile) {
        return postFileRepository.save(postFile);
    }

    @Override
    public List<PostFile> getByPostId(UUID postId) {
        return postFileRepository.findByPostId(postId);
    }

    @Override
    public void delete(PostFile postFile) {
        postFileRepository.delete(postFile);
    }
}
