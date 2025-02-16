package com.tourapi.mandi.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record CompletedCourseDto(
        @Schema(description = "완주 코스 id")
        Long id,

        @Schema(description = "완주 코스 이름")
        String courseName,

        @Schema(description = "코스 완주 소요 시간")
        String duration,

        @Schema(description = "완주 코스 거리")
        BigDecimal distance,

        @Schema(description = "코스 트레킹 경로 이미지 URL")
        String trekkingPathImageUrl,

        @Schema(description = "코스 완주 일시")
        String completedAt
) {
}
