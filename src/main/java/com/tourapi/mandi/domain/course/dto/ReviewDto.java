package com.tourapi.mandi.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ReviewDto(
        @Schema(description = "완주 코스 정보")
        CompletedCourseDto completedCourse,

        @Schema(description = "후기 작성 여부")
        Boolean isReviewed,

        @Schema(description = "후기 내용")
        String content,

        @Schema(description = "후기 점수")
        Integer score,

        @Schema(description = "후기 작성 일시")
        LocalDateTime reviewedAt
) {
}
