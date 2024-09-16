package com.tourapi.mandi.domain.course.entity;

import com.tourapi.mandi.domain.course.CourseExceptionStatus;
import com.tourapi.mandi.global.exception.Exception400;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum DifficultyLevel {
    None(0),
    Easy(1),
    Moderate(2),
    Hard(3);

    private static final Map<Integer, DifficultyLevel> valueToLevel = Arrays.stream(values())
            .collect(Collectors.toMap(
                    level -> level.value,
                    level -> level
            ));

    private final int value;

    DifficultyLevel(int value) {
        this.value = value;
    }

    public static DifficultyLevel of(final Integer level) {
        return Optional.ofNullable(valueToLevel.get(level))
                .orElseThrow(() -> new Exception400(CourseExceptionStatus.DIFFICULTY_INVALID));
    }
}
