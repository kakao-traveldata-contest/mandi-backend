package com.tourapi.mandi.domain.post;


import com.tourapi.mandi.global.exception.BaseExceptionStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PostExceptionStatus implements BaseExceptionStatus {

    POST_NOT_FOUND("해당하는 게시글이 없습니다.", 400, "24000"),
    USER_NOT_AUTHORIZED("권한이 없는 유저입니다.", 403, "24030");

    @Getter
    private final String message;
    @Getter
    private final int status;
    @Getter
    private final String errorCode;

}