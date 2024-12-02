package com.skmservice.domain.post.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final MemberJpaRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<PostReadResponse>> getPost(@PathVariable Long id) {
        PostReadResponse response = postService.getPost(id);
        return ResponseEntity.ok(CommonResponse.onSuccess(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> updatePost(
            @PathVariable Long id,
            @RequestBody PostUpdateRequest request) {
        postService.updatePost(id, request);
        return ResponseEntity.ok(CommonResponse.onSuccess(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(CommonResponse.onSuccess(null));
    }

    @PostMapping
    public ResponseEntity<CommonResponse<PostCreateResponse>> createPost(
            @RequestPart String title,
            @RequestPart String content,
            @RequestPart(required = false) MultipartFile file,
            @RequestHeader("Authorization") String authorizationHeader) {

        // Authorization 헤더에서 Bearer 토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");
        System.out.println("Received Token: " + token);


        // JWT 토큰 유효성 검사
        if (!jwtTokenProvider.validateToken(token)) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }

        // JWT에서 사용자 정보 추출
        String memberId = jwtTokenProvider.getUserPk(token);

        // 회원 조회
        Member member = memberRepository.findByEmail(memberId)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

        // 파일 업로드 및 기타 요청 처리
        PostCreateRequest request = new PostCreateRequest(title, content, file);

        // 게시글 생성 서비스 호출
        PostCreateResponse response = postService.createPost(request, member);

        // 성공적으로 게시글을 생성했으면 응답 반환
        return ResponseEntity.ok(CommonResponse.onSuccess(response));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<Page<PostReadResponse>>> getPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String searchQuery) {

        Page<PostReadResponse> response = postService.getPosts(page, searchQuery);
        return ResponseEntity.ok(CommonResponse.onSuccess(response));
    }
}
