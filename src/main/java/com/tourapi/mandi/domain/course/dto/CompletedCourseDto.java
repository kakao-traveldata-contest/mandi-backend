package com.tourapi.mandi.domain.course.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CompletedCourseDto(
        Long id,
        String courseName,
        BigDecimal distance,
        LocalDateTime completedAt
) {
}
