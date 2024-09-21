package com.tourapi.mandi.domain.course.dto;

import com.tourapi.mandi.domain.course.entity.Course;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CourseNameResponseDto(
        @Schema(description = "코스 id")
        Long id,

        @Schema(description = "코스 이름")
        String name
) {
    public CourseNameResponseDto(Course course) {
        this(course.getCourseId(), course.getName());
    }
}
