package com.skmservice.global.jwt;

import com.skmservice.domain.member.service.UserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    // secretKey를 충분히 긴 문자열로 설정
    private String secretKey = "skmserviceprojectskmserviceprojectskmserviceprojectskmserviceproject";

    // 토큰 유효시간 30일
    private long tokenValidTime = 30 * 24 * 60 * 60 * 1000L;

    private final UserDetailService userDetailService;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        // secretKey를 Base64로 인코딩하여 HS512 알고리즘에 적합하게 만든다.
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // JWT 토큰 생성
    public String createToken(String email, List<String> roles) {
        return Jwts.builder()
                .setSubject(email)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1시간 동안 유효
                .signWith(SignatureAlgorithm.HS512, secretKey)  // HS512 알고리즘 사용
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey) // 직접 secretKey를 사용
                .build() // JwtParser 객체 빌드
                .parseClaimsJws(token)  // 토큰 파싱
                .getBody()
                .getSubject();  // 회원 정보 (userPk) 반환
    }

    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값"
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // "Bearer " 접두어 제거 후 토큰 반환
        }
        return null;  // "Bearer "가 없으면 null 반환
    }

    public boolean validateToken(String jwtToken) {
        try {
            // secretKey를 Base64에서 디코딩
            byte[] decodedKey = Base64.getDecoder().decode(secretKey);

            // JWT 파싱
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(decodedKey)  // 디코딩된 secretKey 사용
                    .build()  // JwtParser 객체 빌드
                    .parseClaimsJws(jwtToken);  // JWT 파싱

            // 만료일자 확인
            Date expiration = claims.getBody().getExpiration();
            if (expiration == null) {
                throw new RuntimeException("만료일자가 없는 토큰입니다.");
            }

            // 만료일자 비교
            return !expiration.before(new Date());
        } catch (io.jsonwebtoken.SignatureException e) {
            // 서명 문제 발생
            System.err.println("서명 오류: " + e.getMessage());
            return false;
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            // 토큰 형식 오류
            System.err.println("잘못된 토큰 형식: " + e.getMessage());
            return false;
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            // 토큰 만료 오류
            System.err.println("토큰 만료: " + e.getMessage());
            return false;
        } catch (Exception e) {
            // 기타 예외
            System.err.println("토큰 검증 중 오류 발생: " + e.getMessage());
            return false;
        }
    }
}
