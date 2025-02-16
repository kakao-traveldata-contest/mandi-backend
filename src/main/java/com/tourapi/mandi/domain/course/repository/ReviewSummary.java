package com.tourapi.mandi.domain.course.repository;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record ReviewSummary(
        BigDecimal averageReviewScore,
        Long excellentCount,
        Long veryGoodCount,
        Long averageCount,
        Long poorCount,
        Long terribleCount
) {
}
