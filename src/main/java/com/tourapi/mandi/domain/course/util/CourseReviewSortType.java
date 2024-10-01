package com.tourapi.mandi.domain.course.util;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CourseReviewSortType {
    LATEST("Latest"),
    HIGHEST_RATING("Highest Rating"),
    LOWEST_RATING("Lowest Rating"),
    ;

    private final String type;
}
