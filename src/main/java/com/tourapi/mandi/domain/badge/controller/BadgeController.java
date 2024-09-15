package com.tourapi.mandi.domain.badge.controller;

import com.tourapi.mandi.domain.badge.dto.BadgeListResponseDto;
import com.tourapi.mandi.domain.badge.service.BadgeService;
import com.tourapi.mandi.global.security.CustomUserDetails;
import com.tourapi.mandi.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "배지 API 목록")
@RestController
@RequiredArgsConstructor
@RequestMapping("/badges")
@Validated
public class BadgeController {

    private final BadgeService badgeService;

    @Operation(summary = "배지 목록 조회")
    @ApiResponse(responseCode = "200", description = "배지 목록 조회 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않은 사용자 ID 입력시")
    @GetMapping
    public ResponseEntity<ApiUtils.ApiResult<BadgeListResponseDto>> googleLogin(
            @Parameter(description = "사용자 ID", required = true)
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(ApiUtils.success(badgeService.getUserBadges(userDetails.user())));
    }
}
