package com.skmservice.domain.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberMappingController {
    // 로그인 페이지
    @GetMapping("/")
    public String showLoginPage() {
        return "login";  // login.html 템플릿을 반환
    }

    // 회원가입 페이지
    @GetMapping("/members/register")
    public String showRegisterPage() {
        return "register";
    }
}
