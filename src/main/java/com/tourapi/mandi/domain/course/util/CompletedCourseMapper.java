package com.tourapi.mandi.domain.course.util;

import com.tourapi.mandi.domain.course.dto.CompletedCourseDto;
import com.tourapi.mandi.domain.course.dto.CompletedCourseListResponseDto;
import com.tourapi.mandi.domain.course.entity.CompletedCourse;
import java.time.Duration;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CompletedCourseMapper {
    public static CompletedCourseDto toCompletedCourseDto(CompletedCourse completedCourse) {
        Duration duration = Duration.between(completedCourse.getStartedAt(), completedCourse.getCompletedAt());

        return CompletedCourseDto.builder()
                .id(completedCourse.getCompletedCourseId())
                .courseName(completedCourse.getCourse().getName())
                .duration(DateTimeUtil.formatDuration(duration))
                .distance(completedCourse.getCourse().getDistance())
                .completedAt(DateTimeUtil.formatDate(completedCourse.getCreatedAt()))
                .build();
    }

    public static CompletedCourseListResponseDto toCompletedCourseListResponseDto(
            int totalCount,
            BigDecimal totalDistance,
            List<CompletedCourseDto> completedCourses
    ) {
        return CompletedCourseListResponseDto.builder()
                .totalCount(totalCount)
                .totalDistance(totalDistance)
                .completedCourses(completedCourses)
                .build();
    }
}
