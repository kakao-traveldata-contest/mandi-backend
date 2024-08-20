package com.tourapi.mandi.domain.badge.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record BadgeResponseDto(
        @Schema(description = "배지 id")
        Long id,
        @Schema(description = "배지 이름")
        String name,
        @Schema(description = "배지 획득 조건")
        String requirements,
        @Schema(description = "배지 이미지")
        String imgUrl
) {
}
