package com.tourapi.mandi.domain.user.controller;

import com.tourapi.mandi.domain.user.dto.NicknameValidationRequestDto;
import com.tourapi.mandi.domain.user.dto.ProfileImageChangeRequestDto;
import com.tourapi.mandi.domain.user.dto.ProfileUpdateRequestDto;
import com.tourapi.mandi.domain.user.service.ProfileService;
import com.tourapi.mandi.global.security.CustomUserDetails;
import com.tourapi.mandi.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "프로필 API 목록")
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
@Validated
public class ProfileController {
    private final ProfileService profileService;

    @Operation(summary = "중복 닉네임 검증")
    @PostMapping("/check-nickname")
    public ResponseEntity<ApiUtils.ApiResult<Boolean>> checkNicknameDuplication(
            @RequestBody @Valid NicknameValidationRequestDto requestDto
    ) {
        return ResponseEntity.ok(ApiUtils.success(profileService.checkNicknameDuplication(requestDto.nickname())));
    }

    @Operation(summary = "프로필 정보 변경(닉네임, 한줄소개)")
    @PatchMapping("/info")
    public ResponseEntity<ApiUtils.ApiResult<Boolean>> updateProfile(
            @RequestBody @Valid ProfileUpdateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(ApiUtils.success(profileService.updateProfile(requestDto, userDetails.user())));
    }

    @Operation(summary = "프로필 사진 변경")
    @PatchMapping("/img")
    public ResponseEntity<ApiUtils.ApiResult<String>> changeProfileImage(
            @RequestBody @Valid ProfileImageChangeRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(ApiUtils.success(profileService.changeProfileImage(requestDto, userDetails.user())));
    }
}
