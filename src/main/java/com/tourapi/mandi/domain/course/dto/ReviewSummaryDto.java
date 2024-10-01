package com.tourapi.mandi.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record ReviewSummaryDto(
        @Schema(description = "후기 총 개수")
        Long totalReviewCount,

        @Schema(description = "후기 평점 평균")
        BigDecimal averageReviewScore,

        @Schema(description = "후기 평점 5점(Excellent) 개수")
        Long excellentCount,

        @Schema(description = "후기 평점 4점(Very Good) 개수")
        Long veryGoodCount,

        @Schema(description = "후기 평점 3점(Average) 개수")
        Long averageCount,

        @Schema(description = "후기 평점 2점(Poor) 개수")
        Long poorCount,

        @Schema(description = "후기 평점 1점(Terrible) 개수")
        Long terribleCount
) {
}
