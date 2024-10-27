package com.tourapi.mandi.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
public record CoursePreferredListResponseDto(
        @Schema(description = "추천 코스 목록")
        List<CourseListItemResponseDto> courses
) {
}