package com.tourapi.mandi.domain.course.util;

import com.tourapi.mandi.domain.course.dto.CompletedCourseDto;
import com.tourapi.mandi.domain.course.entity.CompletedCourse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CompletedCourseMapper {
    public static CompletedCourseDto toCompletedCourseDto(CompletedCourse completedCourse) {
        return CompletedCourseDto.builder()
                .id(completedCourse.getCompletedCourseId())
                .courseName(completedCourse.getCourse().getName())
                .distance(completedCourse.getCourse().getDistance())
                .completedAt(completedCourse.getCreatedAt())
                .build();
    }
}
