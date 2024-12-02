package com.skmservice.domain.post.repository;

import com.skmservice.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    // 제목이나 작성자 ID로 검색
    Page<Post> findByTitleContainingOrMemberIdContaining(String title, String memberId, Pageable pageable);

    // 게시글 저장
    Post save(Post post);

    // 게시글 삭제
    void delete(Post post);

    // 게시글 존재 여부 확인
    boolean existsById(Long id);

    List<Post> findByOrderByCreatedAtDesc(); // createdAt으로 정렬
}
