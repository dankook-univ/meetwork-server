package com.github.dankook_univ.meetwork.post.infra.persistence;

import com.github.dankook_univ.meetwork.post.domain.Post;
import com.github.dankook_univ.meetwork.post.domain.PostRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postRepository;

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> getById(UUID postId) {
        return postRepository.findById(postId);
    }

    @Override
    public List<Post> getByBoardId(UUID boardId, Pageable pageable) {
        return postRepository.findByBoardId(boardId, pageable).getContent();
    }

    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }
}
