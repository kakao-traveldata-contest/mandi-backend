package com.tourapi.mandi.global.config;

import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Duration;

@Profile({ "dev" })
@Configuration
public class RedisConfig {

    @Bean
    public LettuceClientConfigurationBuilderCustomizer lettuceClientConfigCustomize() {
        return builder -> {
            builder.useSsl();
            builder.commandTimeout(Duration.ofSeconds(10));
            builder.shutdownTimeout(Duration.ZERO);
        };
    }
}
