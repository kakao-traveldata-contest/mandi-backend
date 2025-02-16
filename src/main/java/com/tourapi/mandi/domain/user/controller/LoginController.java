package com.tourapi.mandi.domain.user.controller;

import com.tourapi.mandi.domain.user.dto.LoginResponseDto;
import com.tourapi.mandi.domain.user.dto.ReissueDto;
import com.tourapi.mandi.domain.user.dto.SignupRequestDto;
import com.tourapi.mandi.domain.user.dto.oauth.GoogleUserInfo;
import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.domain.user.service.GoogleService;
import com.tourapi.mandi.domain.user.service.UserService;
import com.tourapi.mandi.global.redis.entity.Token;
import com.tourapi.mandi.global.redis.repository.TokenRepository;
import com.tourapi.mandi.global.security.CustomUserDetails;
import com.tourapi.mandi.global.security.JwtProvider;
import com.tourapi.mandi.global.util.ApiUtils;
import com.tourapi.mandi.global.util.ApiUtils.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 google token 입력시"),
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
            @ApiResponse(responseCode = "400",
                    description = "1. 유효하지 않은 access token 입력시\n2. 유효하지 않은 닉네임/한줄소개 입력시\n3. 유효하지 않은 코스 선호도 입력시"
            ),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 google token 입력시"),
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




    @Operation(summary = "로그아웃 ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "404", description = "해당 토큰 값이 Redis에 존재하지 않을시"),
    })
    @PostMapping("/logout")
    public ResponseEntity<ApiResult<?>> logout(HttpServletRequest request)
    {
        userService.logout(request.getHeader(JwtProvider.HEADER));
        return ResponseEntity.ok(ApiUtils.success(null));
    }


    @Operation(summary = "회원탈퇴 ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원탈퇴 성공"),
            @ApiResponse(responseCode = "404", description = "해당하는 정보의 유저가 없을시"),
            @ApiResponse(responseCode = "404", description = "해당 토큰 값이 Redis에 존재하지 않을시")
    })
    @DeleteMapping("/withdrawal")
    public ResponseEntity<ApiResult<Boolean>> withdrawal(
            HttpServletRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        boolean result =userService.withdrawal(userDetails.user(), request.getHeader(JwtProvider.HEADER));
        return ResponseEntity.ok(ApiUtils.success(result));
    }


    @Operation(summary = "토큰 재발급")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공"),
            @ApiResponse(responseCode = "404", description = "해당 토큰 값이 Redis에 존재하지 않을시"),
    })
    @PostMapping("/reissue")
    public ResponseEntity<ApiResult<?>> reissue(
            @RequestBody @Valid ReissueDto.ReissueRequestDto requestDto)
    {
        ReissueDto.ReissueResponseDto resultDto = userService.reissue(requestDto);
        return ResponseEntity.ok(ApiUtils.success(resultDto));
    }


    @Operation(summary = "유저 id 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "id 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 토큰 값의 유저 존재하지 않을 시"),
    })
    @GetMapping("/id")
    public ResponseEntity<ApiResult<Long>> getId(
            @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        User user = userService.getId(userDetails.user());
        return ResponseEntity.ok(ApiUtils.success(user.getUserId()));
    }

}


