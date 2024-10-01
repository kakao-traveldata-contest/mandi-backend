package com.tourapi.mandi.domain.course.repository;

import lombok.Builder;

@Builder
public record ReviewSummary(
        Long totalReviewCount,
        Double averageReviewScore,
        Long excellentCount,
        Long veryGoodCount,
        Long averageCount,
        Long poorCount,
        Long terribleCount
) {
}
