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

import java.util.Date;
import java.util.List;
import java.util.Base64;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

//    @Value("${jwt.secret}")
//    private String secretKey;
// secretKey를 충분히 긴 문자열로 설정
private String secretKey = "skmserviceprojectskmserviceprojectskmserviceprojectskmserviceproject";

    // 토큰 유효시간 30일
    private long tokenValidTime = 30 * 24 * 60 * 60 * 1000L;

    private final UserDetailService userDetailService;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        // secretKey를 Base64로 인코딩하여 HS512 알고리즘에 적합하게 만든다.
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    // JWT 토큰 생성
    public String createToken(String email, List<String> roles) {
        String token = Jwts.builder()
                .setSubject(email)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1시간 동안 유효
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

//        System.out.println("Generated Token: " + token);  // 로그 출력

        return token;
    }

//    public String createToken(String userPk, List<String> roles) {
//        Claims claims = (Claims) Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위, 보통 여기서 user를 식별하는 값을 넣는다.
//        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
//        Date now = new Date();
//        return Jwts.builder()
//                .setClaims(claims) // 정보 저장
//                .setIssuedAt(now) // 토큰 발행 시간 정보
//                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
//                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
//                // signature 에 들어갈 secret값 세팅
//                .compact();
//    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        // Jwts.parserBuilder()를 사용하여 토큰을 파싱합니다.
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes()) // secretKey로 서명 검증
                .build() // JwtParser 객체 빌드
                .parseClaimsJws(token)  // 토큰 파싱
                .getBody()
                .getSubject();  // 회원 정보 (userPk) 반환
    }

    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            // parseClaimsJws()를 호출하려면 parserBuilder()로 JwtParser 객체를 빌드해야 합니다.
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .build() // JwtParser 객체 빌드
                    .parseClaimsJws(jwtToken); // JWT 파싱
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
