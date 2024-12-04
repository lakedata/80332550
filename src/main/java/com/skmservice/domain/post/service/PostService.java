package com.skmservice.domain.post.service;

import com.skmservice.domain.file.service.FileService;
import com.skmservice.domain.member.entity.Member;
import com.skmservice.domain.post.dto.*;
import com.skmservice.domain.post.entity.Post;
import com.skmservice.domain.post.repository.PostJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostJpaRepository postRepository;
    private final FileService fileService;

    @Transactional
    public PostReadResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
        post.setViewCount(post.getViewCount() + 1);

        postRepository.saveAndFlush(post);  // 즉시 DB에 반영

        return PostReadResponse.fromEntity(post);
    }


    // 게시글 생성
    @Transactional
    public PostCreateResponse createPost(PostCreateRequest request, Member member) {
        if (request.title().isEmpty() || request.content().isEmpty()) {
            throw new IllegalArgumentException("제목과 내용은 필수 입력 항목입니다.");
        }

        Post post = Post.toEntity(member, request);
        Post savedPost = postRepository.save(post);
        return PostCreateResponse.fromEntity(savedPost);
    }

    // 게시글 수정
    @Transactional
    public void updatePost(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
        post.update(request.title(), request.content());
        postRepository.save(post);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public Page<PostReadResponse> getPosts(int page, String searchQuery, boolean reverseOrder, String title, String memberId, boolean sortByViewCount) {
        PageRequest pageRequest;

        if (sortByViewCount) {
            pageRequest = PageRequest.of(page - 1, 10, reverseOrder
                    ? Sort.by(Sort.Order.asc("viewCount"))
                    : Sort.by(Sort.Order.desc("viewCount")));
        } else {
            pageRequest = PageRequest.of(page - 1, 10, reverseOrder
                    ? Sort.by(Sort.Order.asc("createdAt"))
                    : Sort.by(Sort.Order.desc("createdAt")));
        }

        Page<Post> posts;

        if (title != null && !title.isEmpty() && memberId != null && !memberId.isEmpty()) {
            posts = postRepository.findByTitleContainingAndMemberEmailContaining(title, memberId, pageRequest);
        } else if (title != null && !title.isEmpty()) {
            posts = postRepository.findByTitleContaining(title, pageRequest);
        } else if (memberId != null && !memberId.isEmpty()) {
            posts = postRepository.findByMemberEmailContaining(memberId, pageRequest);
        } else if (searchQuery != null && !searchQuery.isEmpty()) {
            posts = postRepository.findByTitleContainingOrMemberEmailContaining(searchQuery, searchQuery, pageRequest);
        } else {
            posts = postRepository.findAll(pageRequest);
        }

        return posts.map(PostReadResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
    }
}
