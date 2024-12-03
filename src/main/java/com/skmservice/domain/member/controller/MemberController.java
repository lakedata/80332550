package com.skmservice.domain.member.controller;

import com.skmservice.domain.member.dto.request.MemberCreateRequest;
import com.skmservice.domain.member.dto.request.MemberLoginRequest;
import com.skmservice.domain.member.dto.response.MemberResponse;
import com.skmservice.domain.member.repository.MemberJpaRepository;
import com.skmservice.domain.member.service.MemberService;
import com.skmservice.global.common.CommonResponse;
import com.skmservice.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberJpaRepository memberRepository;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @PostMapping("/register")
    public CommonResponse<?> register(@RequestBody MemberCreateRequest request) {
        return memberService.register(request);
    }

    // 로그인
    @PostMapping("/login")
    public CommonResponse<?> login(@RequestBody MemberLoginRequest request) {
        return memberService.login(request);
    }

    // 회원탈퇴
    @PostMapping("/{id}/delete")
    public CommonResponse<Void> deleteAccount(@PathVariable Long id) {
        return memberService.delete(id);
    }

    // 마이페이지 조회
    @GetMapping("/my-page")
    public CommonResponse<MemberResponse> getMyPage(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        System.out.println("Token: " + token);

        if (!jwtTokenProvider.validateToken(token)) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
        String memberId = jwtTokenProvider.getUserPk(token);
        System.out.println(memberId);
        return memberService.getMyPage(memberId);
    }
}
