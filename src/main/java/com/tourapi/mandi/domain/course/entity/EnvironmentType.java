package com.tourapi.mandi.domain.course.entity;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum EnvironmentType {
    OCEAN(1),
    MOUNTAIN(2),
    OTHER(3);

    private final int number;

    private static final Map<Integer, EnvironmentType> numberToLevel = Arrays.stream(values())
            .collect(Collectors.toMap(
                    level -> level.number,
                    level -> level
            ));

    EnvironmentType(final int number) {
        this.number = number;
    }

    public static Optional<EnvironmentType> get(final Integer level) {
        return Optional.ofNullable(numberToLevel.get(level));
    }
}
