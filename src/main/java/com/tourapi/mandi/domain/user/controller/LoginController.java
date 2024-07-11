package com.tourapi.mandi.domain.user.controller;

import com.tourapi.mandi.domain.user.dto.LoginResponseDto;
import com.tourapi.mandi.domain.user.dto.oauth.GoogleUserInfo;
import com.tourapi.mandi.domain.user.service.GoogleService;
import com.tourapi.mandi.domain.user.service.UserService;
import com.tourapi.mandi.global.util.ApiUtils;
import com.tourapi.mandi.global.util.ApiUtils.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "로그인 API 목록")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final GoogleService googleService;
    private final UserService userService;

    @Operation(summary = "구글 소셜 로그인")
    @PostMapping("/google/login")
    public ResponseEntity<ApiResult<LoginResponseDto>> googleLogin(
            @NotBlank
            @RequestHeader(value = "Google")
            String token
    ) {
        GoogleUserInfo userInfo = googleService.getGoogleUserInfo(token);
        LoginResponseDto resultDto = userService.socialLogin(userInfo);
        return ResponseEntity.ok(ApiUtils.success(resultDto));
    }
}