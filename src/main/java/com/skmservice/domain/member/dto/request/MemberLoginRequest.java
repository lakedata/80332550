package com.skmservice.domain.member.dto.request;

import com.skmservice.domain.member.entity.Member;
import lombok.Builder;

import java.util.Objects;

public record MemberLoginRequest(
        String email,
        String password
) {
    @Builder
    public MemberLoginRequest {
        Objects.requireNonNull(email, "email은 필수입니다.");
    }

    public Member toEntity() {
        return Member.builder()
                .email(this.email)
                .password(this.password)
                .build();
    }
}
