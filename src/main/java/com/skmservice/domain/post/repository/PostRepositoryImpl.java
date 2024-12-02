package com.skmservice.domain.post.repository;

import com.skmservice.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Repository
public class PostRepositoryImpl implements PostRepository {
    private final PostJpaRepository postJpaRepository;

    @Override
    public Page<Post> findByTitleContainingOrMemberIdContaining(String title, String memberId, Pageable pageable) {
        return postJpaRepository.findByTitleContainingOrMemberIdContaining(title, memberId, pageable);
    }
    @Override
    public Post save(Post post) {
        return postJpaRepository.save(post);
    }

    @Override
    public void delete(Post post) {
        postJpaRepository.delete(post);
    }

    @Override
    public boolean existsById(Long id) {
        return postJpaRepository.existsById(id);
    }

    @Override
    public List<Post> findByOrderByCreatedAtDesc() {
        return postJpaRepository.findAll(Sort.by(Sort.Order.desc("createdAt")));
    }
}
