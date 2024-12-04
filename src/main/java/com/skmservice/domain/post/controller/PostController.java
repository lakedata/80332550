package com.skmservice.domain.post.controller;

import com.skmservice.domain.file.service.FileService;
import com.skmservice.domain.member.entity.Member;
import com.skmservice.domain.member.repository.MemberJpaRepository;
import com.skmservice.domain.post.dto.PostCreateRequest;
import com.skmservice.domain.post.dto.PostCreateResponse;
import com.skmservice.domain.post.dto.PostReadResponse;
import com.skmservice.domain.post.dto.PostUpdateRequest;
import com.skmservice.domain.post.service.PostService;
import com.skmservice.global.common.CommonResponse;
import com.skmservice.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final MemberJpaRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PostService postService;
    private final FileService fileService;


    @GetMapping("/{id}")
    public CommonResponse<PostReadResponse> getPost(@PathVariable Long id) {
        PostReadResponse response = postService.getPost(id);
        return CommonResponse.onSuccess(response);
    }

    @PutMapping("/{id}")
    public CommonResponse<Void> updatePost(
            @PathVariable Long id,
            @RequestBody PostUpdateRequest request, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");

        if (!jwtTokenProvider.validateToken(token)) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }

        String memberId = jwtTokenProvider.getUserPk(token);
        System.out.println(memberId);
        Member member = memberRepository.findByEmail(memberId)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));
        postService.updatePost(id, request, member);
        return CommonResponse.onSuccess(null);
    }

    @DeleteMapping("/{id}")
    public CommonResponse<Void> deletePost(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");

        if (!jwtTokenProvider.validateToken(token)) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }

        String memberId = jwtTokenProvider.getUserPk(token);
        System.out.println(memberId);
        Member member = memberRepository.findByEmail(memberId)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

        postService.deletePost(id,member);
        return CommonResponse.onSuccess(null);
    }

    @PostMapping("/add")
    public CommonResponse<PostCreateResponse> createPost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestHeader("Authorization") String authorizationHeader) {

        System.out.println("Title: " + title);
        System.out.println("Content: " + content);
        System.out.println("File name: " + file.getOriginalFilename());

        String token = authorizationHeader.replace("Bearer ", "");

        if (!jwtTokenProvider.validateToken(token)) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }

        String memberId = jwtTokenProvider.getUserPk(token);
        System.out.println(memberId);
        Member member = memberRepository.findByEmail(memberId)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

        // 파일 처리 (파일이 존재할 경우에만)
        String filePath = null;
        boolean fileAttached = false;
        if (file != null) {
            filePath = fileService.storeFile(file);  // 파일을 저장하고 경로를 반환
            fileAttached = true;
        }

        PostCreateRequest request = new PostCreateRequest(title, content, fileAttached, filePath);
        PostCreateResponse response = postService.createPost(request, member);

        return CommonResponse.onSuccess(response);
    }


    @GetMapping
    public CommonResponse<Page<PostReadResponse>> getPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String searchQuery,
            @RequestParam(defaultValue = "false") boolean reverseOrder,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String memberId,
            @RequestParam(defaultValue = "false") boolean sortByViewCount) {

        Page<PostReadResponse> response = postService.getPosts(page, searchQuery, reverseOrder, title, memberId, sortByViewCount);
        return CommonResponse.onSuccess(response);
    }

}
