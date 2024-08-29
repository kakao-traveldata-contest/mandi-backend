package com.tourapi.mandi.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
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
}
