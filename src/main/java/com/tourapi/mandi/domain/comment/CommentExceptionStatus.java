package com.tourapi.mandi.domain.comment;

import com.tourapi.mandi.global.exception.BaseExceptionStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommentExceptionStatus implements BaseExceptionStatus {

    COMMENT_NOT_FOUND("해당하는 댓글이 없습니다.", 404, "44000");

    @Getter
    private final String message;
    @Getter
    private final int status;
    @Getter
    private final String errorCode;

}