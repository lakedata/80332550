package com.skmservice.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로에 대해 CORS 허용
        registry.addMapping("/**")  // 모든 요청 경로에 대해 CORS 적용
                .allowedOrigins("http://localhost:3000")  // 클라이언트 도메인만 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 허용할 HTTP 메서드 설정
                .allowedHeaders("Authorization", "Content-Type", "X-Requested-With")  // 허용할 헤더 설정
                .allowCredentials(true);  // 쿠키와 같은 자격 증명 포함 요청을 허용
    }
}
