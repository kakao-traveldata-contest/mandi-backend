package com.tourapi.mandi.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;


public record ReviewListResponseDto(
        @Schema(description = "완주 코스 총 개수")
        Integer totalCompletedCourseCount,

        @Schema(description = "작성 완료된 후기 총 개수")
        Integer totalReviewCount,

        @Schema(description = "작성 완료된 후기 목록")
        List<ReviewDto> reviewedCourses,

        @Schema(description = "후기 작성 전 완주 코스 목록")
        List<CompletedCourseDto> notReviewedCourses
) {
}
