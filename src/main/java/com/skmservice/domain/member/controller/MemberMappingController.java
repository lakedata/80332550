package com.skmservice.domain.member.controller;

import com.skmservice.domain.member.entity.Member;
import com.skmservice.domain.member.repository.MemberJpaRepository;
import com.skmservice.global.common.CommonResponse;
import com.skmservice.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@RequiredArgsConstructor
@Controller
public class MemberMappingController {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberJpaRepository memberRepository;
    // 로그인 페이지
    @GetMapping("/members/login")
    public String showLoginPage() {
        return "login";  // login.html 반환
    }

    // 회원가입 페이지
    @GetMapping("/members/register")
    public String showRegisterPage() {
        return "register";
    }

    @GetMapping("/members/mypagemap")
    public String showMyPage() {
        return "mypage";
    }

    @CrossOrigin(origins = "*", allowedHeaders = "Authorization")
    @GetMapping("/members/mypage")
        public String showMyPage(@RequestHeader("Authorization") String authorizationHeader, Model model) {

//    public CommonResponse<?> showMyPage(@RequestHeader("Authorization") String authorizationHeader, Model model) {
        String token = authorizationHeader.replace("Bearer ", "");
        System.out.println("Received Token: " + token);

        if (!jwtTokenProvider.validateToken(token)) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
        String memberId = jwtTokenProvider.getUserPk(token);
        Member member = memberRepository.findByEmail(memberId)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));
        model.addAttribute("user", member);  // user 객체를 Model에 추가

        System.out.println(member.getEmail());
//        return CommonResponse.onSuccess(token);
        return "mypage";
    }


    @GetMapping("/")
    public String showMainPage() {
        return "main";
    }
}
