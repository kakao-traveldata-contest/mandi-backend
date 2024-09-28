package com.tourapi.mandi.domain.course;

import com.tourapi.mandi.global.exception.BaseExceptionStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourseExceptionStatus implements BaseExceptionStatus {

    COMPLETED_COURSE_NOT_FOUND("해당하는 코스 완주 기록이 없습니다.", 404, "1"),
    USER_NOT_AUTHORIZED("권한이 없는 유저입니다.", 403, "24030"),
    REVIEW_ALREADY_EXISTS("이미 등록된 후기가 존재합니다.", 409, "2"),

    ;

    private final String message;
    private final int status;
    private final String errorCode;
}
