package com.tourapi.mandi.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
public record CourseNearbyListResponseDto(
        @Schema(description = "코스 목록")
        List<CourseNearbyResponseDto> courses
) {
}
