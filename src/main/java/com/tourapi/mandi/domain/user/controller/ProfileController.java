package com.tourapi.mandi.domain.user.controller;

import com.tourapi.mandi.domain.user.dto.NicknameValidationRequestDto;
import com.tourapi.mandi.domain.user.dto.ProfileImageChangeRequestDto;
import com.tourapi.mandi.domain.user.dto.ProfileInfoResponseDto;
import com.tourapi.mandi.domain.user.dto.ProfileUpdateRequestDto;
import com.tourapi.mandi.domain.user.service.ProfileService;
import com.tourapi.mandi.global.security.CustomUserDetails;
import com.tourapi.mandi.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "프로필 API 목록")
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    @Operation(summary = "중복 닉네임 검증")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "중복되지 않은 닉네임"),
            @ApiResponse(responseCode = "409", description = "중복된 닉네임 입력시")
    })
    @PostMapping("/check-nickname")
    public ResponseEntity<ApiUtils.ApiResult<Boolean>> checkNicknameDuplication(
            @RequestBody @Valid NicknameValidationRequestDto requestDto
    ) {
        return ResponseEntity.ok(ApiUtils.success(profileService.checkNicknameDuplication(requestDto.nickname())));
    }

    @Operation(summary = "프로필 정보 변경(닉네임, 한줄소개)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로필 변경 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않은 사용자 정보 인증시"),
            @ApiResponse(responseCode = "409", description = "중복된 닉네임 입력시")
    })
    @PatchMapping("/info")
    public ResponseEntity<ApiUtils.ApiResult<Boolean>> updateProfile(
            @RequestBody @Valid ProfileUpdateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(ApiUtils.success(profileService.updateProfile(requestDto, userDetails.user())));
    }

    @Operation(summary = "프로필 사진 변경")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로필 사진 변경 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않은 사용자 정보 인증시"),
            @ApiResponse(responseCode = "400", description = "base64 형식으로 인코딩되지 프로필 사진 요청시")
    })
    @PatchMapping("/img")
    public ResponseEntity<ApiUtils.ApiResult<String>> changeProfileImage(
            @RequestBody @Valid ProfileImageChangeRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(ApiUtils.success(profileService.changeProfileImage(requestDto, userDetails.user())));
    }




    @Operation(summary = "프로필 정보 요청")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로필 정보 요청 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않은 사용자 정보 인증시"),
    })
    @GetMapping("/info")
    public ResponseEntity<ApiUtils.ApiResult<ProfileInfoResponseDto>> changeProfileImage(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(ApiUtils.success(profileService.getProfileInfo(userDetails.user())));
    }
}
