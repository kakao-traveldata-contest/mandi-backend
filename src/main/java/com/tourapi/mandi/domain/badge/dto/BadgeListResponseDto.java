package com.tourapi.mandi.domain.badge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;


@Builder
public record BadgeListResponseDto(
        @Schema(description = "전체 뱃지 개수")
        int totalBadgeCount,
        @Schema(description = "사용자 뱃지 개수")
        int userBadgeCount,
        @Schema(description = "사용자 뱃지 목록")
        List<BadgeResponseDto> badges
) {
}
