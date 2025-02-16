package com.tourapi.mandi.domain.course.dto;

import com.tourapi.mandi.global.dto.PageInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
public record CourseReviewListResponseDto(
        @Schema(description = "페이지 정보")
        PageInfoDto pageInfo,

        @Schema(description = "후기 목록 요약 정보")
        ReviewSummaryDto reviewSummary,

        @Schema(description = "후기 목록")
        List<UserReviewDto> reviews
) {
}
