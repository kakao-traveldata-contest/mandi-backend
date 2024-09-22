package com.tourapi.mandi.domain.course.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtil {
    private static final DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public static String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        return String.format("%d:%02d", hours, minutes);
    }

    public static String formatDate(LocalDateTime date) {
        return date.format(defaultFormatter);
    }
}
