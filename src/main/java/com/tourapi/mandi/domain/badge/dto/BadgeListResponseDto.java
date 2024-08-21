package com.tourapi.mandi.domain.badge.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record BadgeListResponseDto(
        @Schema(description = "전체 뱃지 개수")
        int totalBadgeCount,
        @Schema(description = "사용자 뱃지 개수")
        int userBadgeCount,
        @Schema(description = "사용자 뱃지 목록")
        List<BadgeResponseDto> badges
) {
}
