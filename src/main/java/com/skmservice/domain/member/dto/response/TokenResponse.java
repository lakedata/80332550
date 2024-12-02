package com.skmservice.domain.member.dto.response;


import com.skmservice.domain.member.vo.Token;
import lombok.Builder;

@Builder
public record TokenResponse(
        String accessToken,
        String refreshToken
) {

    public static TokenResponse of(Token token) {
        return TokenResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }
}
