package com.tourapi.mandi.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourapi.mandi.global.exception.Exception401;
import com.tourapi.mandi.global.exception.Exception403;
import com.tourapi.mandi.global.redis.service.BlackListTokenService;
import com.tourapi.mandi.global.util.FilterResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final BlackListTokenService blackListTokenService;
    private final ObjectMapper objectMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

        // CSRF 해제
        http.csrf(AbstractHttpConfigurer::disable);

        // iframe 거부
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        // cors 재설정
        http.cors(corsConfigurer -> corsConfigurer.configurationSource(configurationSource()));

        // jSessionId 사용 거부
        http.sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // form 로그인 해제
        http.formLogin(AbstractHttpConfigurer::disable);

        // 로그인 인증창 비활성화 (HTTP Basic 비활성화)
        http.httpBasic(AbstractHttpConfigurer::disable);

        // 커스텀 필터 적용
        http.addFilter(new JwtAuthenticationFilter(authenticationManager, blackListTokenService));
        http.addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class);

        // 인증 실패 처리
        http.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint((request, response, authException) -> FilterResponseUtils.unAuthorized(response, new Exception401(SecurityExceptionStatus.UNAUTHORIZED))));

        // 권한 실패 처리
        http.exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler((request, response, authException) -> FilterResponseUtils.forbidden(response, new Exception403(SecurityExceptionStatus.FORBIDDEN))));

        http.exceptionHandling((exceptionHandling) -> exceptionHandling
                .accessDeniedHandler(new Http403Handler(objectMapper))
                .authenticationEntryPoint(new Http401Handler(objectMapper))
        );

        // 인증, 권한 필터 설정
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(new AntPathRequestMatcher("/auth/change-profile"))
                .authenticated()
                .anyRequest().permitAll());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}



