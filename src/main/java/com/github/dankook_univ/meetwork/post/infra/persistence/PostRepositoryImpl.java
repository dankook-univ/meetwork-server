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
    public Optional<Post> getById(String postId) {
        return postRepository.findById(UUID.fromString(postId));
    }

    @Override
    public List<Post> getByBoardId(String boardId, Pageable pageable) {
        return postRepository.findByBoardId(UUID.fromString(boardId), pageable).getContent();
    }

    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }

    @Override
    public void deleteByWriterId(String writerId) {
        postRepository.deleteAllByWriterId(UUID.fromString(writerId));
    }

    @Override
    public void deleteByBoardId(String boardId) {
        postRepository.deleteAllByBoardId(UUID.fromString(boardId));
    }
}
