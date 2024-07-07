package com.tourapi.mandi.domain.user.controller;


import com.tourapi.mandi.domain.user.UserExceptionStatus;
import com.tourapi.mandi.domain.user.dto.LoginResponseDto;
import com.tourapi.mandi.domain.user.dto.oauth.GoogleUserInfo;
import com.tourapi.mandi.domain.user.service.GoogleService;
import com.tourapi.mandi.domain.user.service.UserService;
import com.tourapi.mandi.global.exception.Exception400;
import com.tourapi.mandi.global.util.ApiUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final GoogleService googleService;
    private final UserService userService;

    @PostMapping("/google/login")
    public ResponseEntity<?> googleLogin(HttpServletRequest request) {
        String token = Optional.of(request.getHeader("Google")).orElseThrow(
                () -> new Exception400(UserExceptionStatus.GOOGLE_TOKEN_MISSING));
        GoogleUserInfo userInfo = googleService.getGoogleUserInfo(token);
        LoginResponseDto resultDto = userService.socialLogin(userInfo);
        return ResponseEntity.ok(ApiUtils.success(resultDto));
    }
}