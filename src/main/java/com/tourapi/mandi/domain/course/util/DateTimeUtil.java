package com.tourapi.mandi.domain.course.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import com.tourapi.mandi.domain.course.CourseExceptionStatus;
import com.tourapi.mandi.global.exception.Exception500;

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

	/**
	 * "7h" 또는 "3h 30m" 형식의 문자열을 "시간:분" 형식으로 변환합니다.
	 */
	public static String formatHourMinute(LocalTime hourMinute) {
		if (hourMinute == null) {
			throw new Exception500(CourseExceptionStatus.COURSE_DURATION_INVALID_FORMAT);
		}
		int hours = hourMinute.getHour();
		int minutes = hourMinute.getMinute();

		if (hours > 0 && minutes > 0) {
			return String.format("%dh %dm", hours, minutes);
		} else if (hours > 0) {
			return String.format("%dh", hours);
		} else {
			return String.format("%dm", minutes);
		}
	}
}
