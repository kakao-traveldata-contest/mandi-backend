package com.tourapi.mandi.global.util;
import com.tourapi.mandi.global.exception.BaseExceptionStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UtilExceptionStatus implements BaseExceptionStatus {
    S3_IMAGE_DELETE_FAILED("S3 이미지 삭제에 실패했습니다.", 500, "05000"),
    NOT_BASE64_DATA("Base64 형식이 아닙니다.", 400, "04005"),
    IMAGE_INVALID_DATA("유효하지 않은 이미지 데이터이거나, 이미지 데이터가 아닙니다.", 400, "04006"),

    IMAGE_UNREADABLE_DATA("이미지가 손상되었거나 읽을 수 없는 형식입니다.", 400, "04007");

//    IMAGE_INVALID("이미지 데이터가 아니거나, 유효하지 않은 이미지 링크입니다.", 400, "04008"),

//    IMAGE_UNREADABLE_URL("URL 형식이 아닙니다.", 400, "04009");

    private final String message;
    private final int status;
    private final String errorCode;

}
