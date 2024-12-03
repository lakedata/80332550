package com.skmservice.domain.member.service;

import com.skmservice.domain.member.dto.request.MemberCreateRequest;
import com.skmservice.domain.member.dto.request.MemberLoginRequest;
import com.skmservice.domain.member.dto.response.MemberResponse;
import com.skmservice.domain.member.entity.Member;
import com.skmservice.domain.member.repository.MemberJpaRepository;
import com.skmservice.global.common.CommonResponse;
import com.skmservice.global.exception.CustomException;
import com.skmservice.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberJpaRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public CommonResponse<Member> register(MemberCreateRequest request) {
        if (memberRepository.findByEmail(request.email()).isPresent()) {
            throw new CustomException(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.");
        }

        Member member = new Member(request);
        Member savedMember = memberRepository.save(member);

        return CommonResponse.onSuccess(savedMember);
    }

    @Transactional
    public CommonResponse<String> login(MemberLoginRequest request) {
        Member member = memberRepository.findByEmailAndPassword(request.email(), request.password())
                .orElseThrow(() -> new CustomException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 잘못되었습니다."));

        String token = jwtTokenProvider.createToken(member.getEmail(), List.of("ROLE_USER"));

        return CommonResponse.onSuccess(token);
    }

    @Transactional
    public CommonResponse<Void> delete(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."));
        memberRepository.delete(member);

        return CommonResponse.onSuccess();
    }


    // 마이페이지 조회
    public CommonResponse<MemberResponse> getMyPage(String memberId) {
        Member member = memberRepository.findByEmail(memberId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."));

        MemberResponse response = new MemberResponse(member.getName(), member.getEmail());

        return CommonResponse.onSuccess(response);
    }

}
