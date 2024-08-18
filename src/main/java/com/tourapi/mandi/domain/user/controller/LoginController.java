package com.tourapi.mandi.domain.user.controller;

import com.tourapi.mandi.domain.user.dto.LoginResponseDto;
import com.tourapi.mandi.domain.user.dto.NicknameValidationRequestDto;
import com.tourapi.mandi.domain.user.dto.SignupRequestDto;
import com.tourapi.mandi.domain.user.dto.oauth.GoogleUserInfo;
import com.tourapi.mandi.domain.user.service.GoogleService;
import com.tourapi.mandi.domain.user.service.UserService;
import com.tourapi.mandi.global.util.ApiUtils;
import com.tourapi.mandi.global.util.ApiUtils.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "로그인 API 목록")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Validated
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




    @Operation(summary = "구글 소셜 회원가입 ")
    @PostMapping("/google/signup")
    public ResponseEntity<ApiResult<LoginResponseDto>> googleSignup(
            @NotBlank
            @RequestHeader(value = "Google") String token,
            @RequestBody @Valid SignupRequestDto requestDto


    ) {
        GoogleUserInfo userInfo = googleService.getGoogleUserInfo(token);
        LoginResponseDto resultDto = userService.socialSignup(userInfo,requestDto);
        return ResponseEntity.ok(ApiUtils.success(resultDto));
    }

    @Operation(summary = "중복 닉네임 검증")
    @PostMapping("/check-nickname")
    public ResponseEntity<ApiResult<Boolean>> checkNicknameDuplication(
            @RequestBody @Valid NicknameValidationRequestDto requestDto
    ) {
        return ResponseEntity.ok(ApiUtils.success(userService.checkNicknameDuplication(requestDto)));
    }
}