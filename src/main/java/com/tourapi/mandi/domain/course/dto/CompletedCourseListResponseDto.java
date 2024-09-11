package com.tourapi.mandi.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;

@Builder
public record CompletedCourseListResponseDto(
        @Schema(description = "완주한 코스 총 개수")
        Integer totalCount,

        @Schema(description = "완주한 코스 총 거리")
        BigDecimal totalDistance,

        @Schema(description = "완주한 코스 정보")
        List<CompletedCourseDto> completedCourses
) {
}
