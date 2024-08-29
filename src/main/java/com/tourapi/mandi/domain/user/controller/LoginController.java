package com.tourapi.mandi.domain.user.controller;

import com.tourapi.mandi.domain.user.dto.LoginResponseDto;
import com.tourapi.mandi.domain.user.dto.SignupRequestDto;
import com.tourapi.mandi.domain.user.dto.oauth.GoogleUserInfo;
import com.tourapi.mandi.domain.user.service.GoogleService;
import com.tourapi.mandi.domain.user.service.UserService;
import com.tourapi.mandi.global.util.ApiUtils;
import com.tourapi.mandi.global.util.ApiUtils.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 access token 입력시"),
            @ApiResponse(responseCode = "500", description = "Google API 연동 중 문제 발생시"),
    })
    @PostMapping("/google/login")
    public ResponseEntity<ApiResult<LoginResponseDto>> googleLogin(
            @NotBlank
            @RequestHeader(value = "Google")
            @Parameter(description = "Google access token", required = true) String token
    ) {
        GoogleUserInfo userInfo = googleService.getGoogleUserInfo(token);
        LoginResponseDto resultDto = userService.socialLogin(userInfo);
        return ResponseEntity.ok(ApiUtils.success(resultDto));
    }

    @Operation(summary = "구글 소셜 회원가입 ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "1. 유효하지 않은 access token 입력시\n2. 유효하지 않은 닉네임/한줄소개 입력시"),
            @ApiResponse(responseCode = "500", description = "Google API 연동 중 문제 발생시"),
    })
    @PostMapping("/google/signup")
    public ResponseEntity<ApiResult<LoginResponseDto>> googleSignup(
            @NotBlank
            @RequestHeader(value = "Google")
            @Parameter(description = "Google access token", required = true) String token,
            @RequestBody @Valid SignupRequestDto requestDto
    ) {
        GoogleUserInfo userInfo = googleService.getGoogleUserInfo(token);
        LoginResponseDto resultDto = userService.socialSignup(userInfo,requestDto);
        return ResponseEntity.ok(ApiUtils.success(resultDto));
    }
}


