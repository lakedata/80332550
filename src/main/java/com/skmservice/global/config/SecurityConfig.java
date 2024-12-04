package com.skmservice.global.config;

import com.skmservice.global.jwt.JwtAuthenticationFilter;
import com.skmservice.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .requestMatchers("/", "/Index", "/h2-console/**").permitAll()
                                .requestMatchers("/members/**", "/posts/**", "/login", "/members/register", "/images/**", "/css/**", "/js/**", "/images/**").permitAll()
                                .requestMatchers("/members/my-page").permitAll()
                                .requestMatchers("/favicon.ico").permitAll()  // favicon.ico에 대한 접근을 허용
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
