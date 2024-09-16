package com.tourapi.mandi.domain.course;

import com.tourapi.mandi.global.exception.BaseExceptionStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CourseExceptionStatus implements BaseExceptionStatus {
    DIFFICULTY_INVALID("유효하지 않은 난이도 정보입니다.", 400, "14000");

    @Getter
    private final String message;
    @Getter
    private final int status;
    @Getter
    private final String errorCode;

}