package com.tourapi.mandi.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .components(bearerTokenComponents())
                .addSecurityItem(bearerTokenRequirement());
    }

    private Info apiInfo() {
        return new Info()
                .title("만디 API 문서")
                .version("1.0.0");
    }

    private Components bearerTokenComponents() {
        return new Components().addSecuritySchemes("Bearer Token", jwtSecurityScheme());
    }

    private SecurityScheme jwtSecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .name("Authorization")
                .scheme("Bearer")
                .bearerFormat("JWT");
    }

    private SecurityRequirement bearerTokenRequirement() {
        return new SecurityRequirement().addList("Bearer Token");
    }

    @Bean
    public GroupedOpenApi authOpenApi() {
        return GroupedOpenApi.builder()
                .group("로그인 API")
                .pathsToMatch("/auth/**", "/dev/**")
                .build();
    }

    @Bean
    public GroupedOpenApi profileOpenApi() {
        return GroupedOpenApi.builder()
                .group("프로필 API")
                .pathsToMatch("/profile/**")
                .build();
    }

    @Bean
    public GroupedOpenApi badgeOpenApi() {
        return GroupedOpenApi.builder()
                .group("뱃지 API")
                .pathsToMatch("/badges/**")
                .build();
    }

    @Bean
    public GroupedOpenApi courseOpenApi() {
        return GroupedOpenApi.builder()
                .group("코스 API")
                .pathsToMatch("/courses/**")
                .build();
    }

    @Bean
    public GroupedOpenApi completedCourseOpenApi() {
        return GroupedOpenApi.builder()
                .group("후기 API")
                .pathsToMatch("/reviews/**")
                .build();
    }

    @Bean
    public GroupedOpenApi postOpenApi() {
        return GroupedOpenApi.builder()
                .group("게시글 API")
                .pathsToMatch("/posts/**")
                .build();
    }

    @Bean
    public GroupedOpenApi commentOpenApi() {
        return GroupedOpenApi.builder()
                .group("댓글 API")
                .pathsToMatch("/comments/**")
                .build();
    }

    @Bean
    public GroupedOpenApi trekkingOpenApi() {
        return GroupedOpenApi.builder()
            .group("트레킹 API")
            .pathsToMatch("/trekking/**")
            .build();
    }
}
