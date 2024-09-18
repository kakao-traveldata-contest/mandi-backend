package com.tourapi.mandi.domain.course.util;

import static lombok.AccessLevel.PRIVATE;

import com.tourapi.mandi.domain.course.dto.CompletedCourseDto;
import com.tourapi.mandi.domain.course.dto.ReviewDto;
import com.tourapi.mandi.domain.course.dto.ReviewListResponseDto;
import com.tourapi.mandi.domain.course.entity.CompletedCourse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReviewMapper {
    public static ReviewDto toReviewDto(CompletedCourse completedCourse) {
        CompletedCourseDto completedCourseDto = CompletedCourseMapper.toCompletedCourseDto(completedCourse);

        return ReviewDto.builder()
                .completedCourse(completedCourseDto)
                .isReviewed(completedCourse.getIsReviewed())
                .content(completedCourse.getReviewContent())
                .score(completedCourse.getReviewScore())
                .reviewedAt(completedCourse.getReviewedAt())
                .build();
    }

    public static ReviewListResponseDto toReviewListResponseDto(
            int totalCompletedCourseCount,
            int totalReviewCount,
            List<ReviewDto> reviewedCourses,
            List<CompletedCourseDto> notReviewedCourses
    ) {
        return ReviewListResponseDto.builder()
                .totalCompletedCourseCount(totalCompletedCourseCount)
                .totalReviewCount(totalReviewCount)
                .reviewedCourses(reviewedCourses)
                .notReviewedCourses(notReviewedCourses)
                .build();
    }
}
