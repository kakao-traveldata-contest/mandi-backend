package com.tourapi.mandi.domain.course.entity;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum DurationLevel {
    EASY(1, 0, 3),
    MODERATE(2, 3, 6),
    CHALLENGING(3, 6, 9),
    ADVENTURE(4, 9, 23);

    private static final Map<Integer, DurationLevel> numberToLevel = Arrays.stream(values())
            .collect(Collectors.toMap(
                    level -> level.number,
                    level -> level
            ));

    private final Integer number;
    private final Integer startHoursInclusive;
    private final Integer endHoursInclusive;

    DurationLevel(
            final Integer number,
            final Integer startHoursInclusive,
            final Integer endHoursInclusive
    ) {
        this.number = number;
        this.startHoursInclusive = startHoursInclusive;
        this.endHoursInclusive = endHoursInclusive;
    }

    public static Optional<DurationLevel> get(final Integer level) {
        return Optional.ofNullable(numberToLevel.get(level));
    }

    public LocalTime getStartInclusive() {
        return LocalTime.of(startHoursInclusive, 0);
    }

    public LocalTime getEndInclusive() {
        return LocalTime.of(endHoursInclusive, 59);
    }
}
