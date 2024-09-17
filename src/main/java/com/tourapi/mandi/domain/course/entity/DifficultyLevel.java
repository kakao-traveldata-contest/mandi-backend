package com.tourapi.mandi.domain.course.entity;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum DifficultyLevel {
    None(0),
    Easy(1),
    Moderate(2),
    Hard(3);

    private static final Map<Integer, DifficultyLevel> numberToLevel = Arrays.stream(values())
            .collect(Collectors.toMap(
                    level -> level.number,
                    level -> level
            ));

    private final int number;

    DifficultyLevel(int number) {
        this.number = number;
    }

    public static Optional<DifficultyLevel> get(Integer level) {
        return Optional.ofNullable(numberToLevel.get(level));
    }
}
