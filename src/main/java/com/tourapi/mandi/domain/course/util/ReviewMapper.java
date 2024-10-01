package com.tourapi.mandi.domain.course.util;

import com.tourapi.mandi.domain.course.dto.CompletedCourseDto;
import com.tourapi.mandi.domain.course.dto.CourseReviewListResponseDto;
import com.tourapi.mandi.domain.course.dto.ReviewDto;
import com.tourapi.mandi.domain.course.dto.ReviewImageDto;
import com.tourapi.mandi.domain.course.dto.ReviewListResponseDto;
import com.tourapi.mandi.domain.course.dto.ReviewSummaryDto;
import com.tourapi.mandi.domain.course.dto.UserReviewDto;
import com.tourapi.mandi.domain.course.entity.CompletedCourse;
import com.tourapi.mandi.domain.course.entity.ReviewImage;
import com.tourapi.mandi.domain.course.repository.ReviewSummary;
import com.tourapi.mandi.domain.user.util.UserMapper;
import com.tourapi.mandi.global.dto.PageInfoDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

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

    public static CourseReviewListResponseDto toCourseReviewListResponseDto(
            Page<CompletedCourse> page,
            ReviewSummary reviewSummary
    ) {
        List<UserReviewDto> reviews = page.stream()
                .map(ReviewMapper::toUserReviewDto)
                .toList();

        return CourseReviewListResponseDto.builder()
                .pageInfo(new PageInfoDto(page))
                .reviewSummary(toReviewSummaryDto(page.getTotalElements(), reviewSummary))
                .reviews(reviews)
                .build();
    }

    private static ReviewSummaryDto toReviewSummaryDto(Long totalCount, ReviewSummary reviewSummary) {
        return ReviewSummaryDto.builder()
                .totalReviewCount(totalCount)
                .averageReviewScore(reviewSummary.averageReviewScore())
                .excellentCount(reviewSummary.excellentCount())
                .veryGoodCount(reviewSummary.veryGoodCount())
                .averageCount(reviewSummary.averageCount())
                .poorCount(reviewSummary.poorCount())
                .terribleCount(reviewSummary.terribleCount())
                .build();
    }

    private static UserReviewDto toUserReviewDto(CompletedCourse review) {
        return UserReviewDto.builder()
                .user(UserMapper.toUserDto(review.getUser()))
                .review(toReviewDto(review))
                .build();
    }

    private static ReviewImageDto toReviewImageDto(ReviewImage reviewImage) {
        return ReviewImageDto.builder()
                .url(reviewImage.getUrl())
                .build();
    }
}
