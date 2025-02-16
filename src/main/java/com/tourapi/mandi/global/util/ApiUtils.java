package com.tourapi.mandi.global.util;


import io.swagger.v3.oas.annotations.media.Schema;

public class ApiUtils {
    public static <T> ApiResult<T> success(T response) {
        return new ApiResult<>(true, response, null);
    }

    public static ApiResult<?> error(String message, int status, String errorCode) {
        return new ApiResult<>(false, null, new ApiError(message, status, errorCode));
    }

    @Schema(description = "정상 처리 시 응답 DTO")
    public record ApiResult<T>(
            @Schema(description = "응답 성공 여부") boolean success,
            @Schema(description = "응답 본문") T response,
            @Schema(description = "에러 발생시 상세 기재", nullable = true) ApiError error
    ) {
    }

    @Schema(description = "에러 발생 시 응답 DTO")
    public record ApiError(
            @Schema(description = "오류 메시지") String message,
            @Schema(description = "HTTP 상태 코드") int status,
            @Schema(description = "애러 코드") String errorCode
    ) {
    }
}
