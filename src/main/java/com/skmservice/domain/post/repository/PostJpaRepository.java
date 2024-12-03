package com.skmservice.domain.post.repository;

import com.skmservice.domain.post.entity.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostJpaRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(Long postId);
    Page<Post> findByTitleContainingOrMemberIdContaining(String title, String memberId, Pageable pageable);

    Page<Post> findByTitleContaining(String title, Pageable pageable);

    Page<Post> findByMemberEmailContaining(String memberEmail, Pageable pageable);

    Page<Post> findByTitleContainingAndMemberEmailContaining(String title, String memberEmail, Pageable pageable);

    Page<Post> findByTitleContainingOrMemberEmailContaining(String title, String memberEmail, Pageable pageable);
}
