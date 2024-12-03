package com.skmservice.domain.member.dto.response;

import com.skmservice.domain.member.entity.Member;

public record MemberResponse(String email, String name) {

    // Entity 객체를 받아서 MemberResponse 객체로 변환하는 메서드
    public static MemberResponse fromEntity(Member member) {
        return new MemberResponse(
                member.getEmail(),
                member.getName()
        );
    }
}

