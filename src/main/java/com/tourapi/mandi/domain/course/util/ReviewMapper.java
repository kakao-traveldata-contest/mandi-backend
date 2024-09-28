package com.tourapi.mandi.domain.course.util;

import com.tourapi.mandi.domain.course.dto.CompletedCourseDto;
import com.tourapi.mandi.domain.course.dto.ReviewDto;
import com.tourapi.mandi.domain.course.dto.ReviewImageDto;
import com.tourapi.mandi.domain.course.dto.ReviewListResponseDto;
import com.tourapi.mandi.domain.course.entity.CompletedCourse;
import com.tourapi.mandi.domain.course.entity.ReviewImage;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReviewMapper {
    public static ReviewDto toReviewDto(CompletedCourse completedCourse) {
        CompletedCourseDto completedCourseDto = CompletedCourseMapper.toCompletedCourseDto(completedCourse);
        List<ReviewImageDto> reviewImageList = completedCourse.getReviewImageList().stream()
                .map(ReviewMapper::toReviewImageDto)
                .toList();

        return ReviewDto.builder()
                .completedCourse(completedCourseDto)
                .isReviewed(completedCourse.getIsReviewed())
                .content(completedCourse.getReviewContent())
                .score(completedCourse.getReviewScore())
                .imageUrlList(reviewImageList)
                .reviewedAt(DateTimeUtil.formatDate(completedCourse.getReviewedAt()))
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

    public static ReviewImage toReviewImage(String url, CompletedCourse completedCourse) {
        return ReviewImage.builder()
                .completedCourse(completedCourse)
                .url(url)
                .build();
    }

    private static ReviewImageDto toReviewImageDto(ReviewImage reviewImage) {
        return ReviewImageDto.builder()
                .url(reviewImage.getUrl())
                .build();
    }
}
