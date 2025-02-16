package com.tourapi.mandi.global.security;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;

import com.tourapi.mandi.global.util.ApiUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class Http401Handler implements AuthenticationEntryPoint {

    private static final String ERR_MESSAGE = "로그인이 필요합니다.";

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.error("[인증오류] {}", ERR_MESSAGE);
        setForbiddenResponse(request, response, authException);
    }

    private void setForbiddenResponse(HttpServletRequest request, HttpServletResponse response, Throwable exception)
            throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getOutputStream()
                .write(objectMapper.writeValueAsBytes(
                        ApiUtils.error(exception.getMessage(),
                                HttpStatus.FORBIDDEN.value(),
                                "04030")));
    }
}