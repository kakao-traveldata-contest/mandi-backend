package com.tourapi.mandi.domain.user;


import com.tourapi.mandi.global.exception.BaseExceptionStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public enum UserExceptionStatus implements BaseExceptionStatus {
    GOOGLE_TOKEN_INVALID("유효하지 않은 구글 토큰입니다.", 400, "14000"),
    REFRESH_TOKEN_INVALID("유효하지 않은 토큰입니다.", 400, "14001"),
    REFRESH_TOKEN_EXPIRED("유효기간이 만료된 토큰입니다.", 400, "14002"),
    GOOGLE_TOKEN_MISSING("구글 토큰를 입력하지 않았습니다.", 400, "14003"),
    USER_NOT_FOUND("회원이 존재하지 않습니다", 404, "14040"),
    NICKNAME_ALREADY_EXISTS("이미 사용 중인 닉네임입니다. 다른 닉네임을 입력하세요.", 409, "14090"),
    GOOGLE_API_CONNECTION_ERROR("구글 API 연동 중 문제가 발생했습니다", 500, "15000"),
    PROFILE_ARGUMENTS_INVALID("닉네임 또는 한줄소개 중 하나는 반드시 입력해야 합니다.", 400, "14004"),
    USER_NOT_AUTHORIZED("권한이 없는 유저입니다.", 403, "14030");

    @Getter
    private final String message;
    @Getter
    private final int status;
    @Getter
    private final String errorCode;

}