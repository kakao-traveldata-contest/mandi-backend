package com.tourapi.mandi.global.security;

import com.tourapi.mandi.global.exception.BaseExceptionStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum SecurityExceptionStatus implements BaseExceptionStatus {
    UNAUTHORIZED("인증되지 않았습니다.", 401, "04010"),
    FORBIDDEN("권한이 없습니다.", 403, "04030");

    private final String message;
    private final int status;
    private final String errorCode;

}
