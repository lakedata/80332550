package com.skmservice.domain.member.controller;

import com.skmservice.domain.member.dto.request.MemberCreateRequest;
import com.skmservice.domain.member.dto.request.MemberLoginRequest;
import com.skmservice.domain.member.service.MemberService;
import com.skmservice.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    // 회원가입 처리
    @PostMapping("/register")
    public CommonResponse<?> register(@RequestBody MemberCreateRequest request) {
        return memberService.register(request);
    }

    //로그인 처리
//    @PostMapping("/login")
//    public CommonResponse<?> login(@RequestParam String email, @RequestParam String password) {
//        return memberService.login(email, password);
//    }

    @PostMapping("/login")
    public CommonResponse<?> login(@RequestBody MemberLoginRequest request) {
        return memberService.login(request);
    }

    // 로그아웃 처리
    @PostMapping("/logout")
    public String logout() {
        // 로그아웃 처리 (예시: 세션 제거, JWT 무효화 등)
        return "redirect:/login";
    }

    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable Long id) {
        return memberService.delete(id);
    }
}
