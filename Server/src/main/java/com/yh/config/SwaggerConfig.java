package com.yh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))) // Bearer 인증 설정 추가
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth")) // 인증 정보 포함
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("yh project")
                .description("<h3>yh project swagger 테스트</h3>")
                .version("1.0.0");
    }
}
