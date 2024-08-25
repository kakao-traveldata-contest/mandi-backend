package com.tourapi.mandi.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourapi.mandi.global.util.ApiUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class Http403Handler implements AccessDeniedHandler {

    private static final String ERR_MESSAGE = "리소스에 접근할 수 없습니다.";

    private final ObjectMapper objectMapper;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error(ERR_MESSAGE);
        setForbiddenResponse(request, response, accessDeniedException);
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