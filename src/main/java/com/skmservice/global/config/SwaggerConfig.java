package com.skmservice.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("SK m&service 게시판 API 명세서")
                .version("v2.0")
                .description("SK m&service의 게시판 관리 시스템 API 문서입니다.");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
