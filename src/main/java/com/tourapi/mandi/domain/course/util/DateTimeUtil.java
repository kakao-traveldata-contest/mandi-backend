package com.tourapi.mandi.domain.course.util;

import java.time.Duration;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtil {
    public static String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        return String.format("%d:%02d", hours, minutes);
    }
}
