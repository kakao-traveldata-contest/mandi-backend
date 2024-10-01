package com.tourapi.mandi.domain.course.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.tourapi.mandi.domain.course.CourseExceptionStatus;
import com.tourapi.mandi.global.exception.Exception500;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtil {
	private static final DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
	private static final Pattern durationPattern = Pattern.compile("(?:(\\d+)h)?\\s*(?:(\\d+)m)?");

	private static final String hourMinuteFormat = "%d:%02d";
	private static final int HOUR = 1;
	private static final int MINUTE = 2;

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
	public static String formatHourMinute(String hourMinute) {
		Matcher matcher = durationPattern.matcher(hourMinute);

		if (matcher.find()) {
			return String.format(hourMinuteFormat, doFormat(matcher, HOUR), doFormat(matcher, MINUTE));
		}
		throw new Exception500(CourseExceptionStatus.COURSE_DURATION_INVALID_FORMAT);
	}

	private static int doFormat(Matcher matcher, int groupNumber) {
		if (matcher.group(groupNumber) == null) {
			return 0;
		}
		return Integer.parseInt(matcher.group(groupNumber));
	}
}
