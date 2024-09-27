package com.tourapi.mandi.domain.course;


import com.tourapi.mandi.global.exception.BaseExceptionStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CourseExceptionStatus implements BaseExceptionStatus {

	COURSE_NOT_FOUND("해당하는 게시글이 없습니다.", 404, "24040"),
	COURSE_DURATION_INVALID_FORMAT("코스 소요 시간 형식이 유효하지 않습니다.", 500, "24041");

	@Getter
	private final String message;
	@Getter
	private final int status;
	@Getter
	private final String errorCode;

}
