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

    @Transactional(readOnly = false)
    public PostReadResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);

        postRepository.flush();  // 강제로 DB에 반영
        System.out.println("After increment view count: " + post.getViewCount());

        return PostReadResponse.fromEntity(post);
    }

    @Transactional
    public PostCreateResponse createPost(PostCreateRequest request, Member member) {
        // 파일 처리
        String filePath = null;
        if (request.file() != null) {
            filePath = fileService.storeFile(request.file());  // 파일을 저장하고 경로를 반환받음
        }

        // 게시글 생성
        if (request.title().isEmpty() || request.content().isEmpty()) {
            throw new IllegalArgumentException("제목과 내용은 필수 입력 항목입니다.");
        }

        Post post = new Post(request, member, filePath); // 파일 경로를 Post 생성 시 전달
        Post savedPost = postRepository.save(post);
        return PostCreateResponse.fromEntity(savedPost);
    }

    @Transactional
    public void updatePost(Long id, PostUpdateRequest request) {
        // 게시글 수정
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
        post.update(request.title(), request.content());
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        // 게시글 삭제
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public Page<PostReadResponse> getPosts(int page, String searchQuery) {
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Order.desc("createdAt")));
        Page<Post> posts;

        if (searchQuery != null && !searchQuery.isEmpty()) {
            posts = postRepository.findByTitleContainingOrMemberIdContaining(searchQuery, searchQuery, pageRequest);
        } else {
            posts = postRepository.findAll(pageRequest);
        }

        return posts.map(PostReadResponse::fromEntity);
    }

    // 새로운 getPostById 메서드 추가
    @Transactional(readOnly = true)
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
    }


}
