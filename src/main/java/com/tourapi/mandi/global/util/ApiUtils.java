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
            boolean success,
            T response,
            ApiError error
    ) {
    }

    @Schema(description = "에러 발생 시 응답 DTO")
    public record ApiError(
            String message,
            int status,
            String errorCode
    ) {
    }
}
