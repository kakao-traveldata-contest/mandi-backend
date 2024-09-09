package com.tourapi.mandi.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "로그인 응답 DTO")
public record LoginResponseDto(
        @Schema(description = "리프레시 토큰")
        String refreshToken,
        @Schema(description = "엑세스 토큰")
        String accessToken,
        @Schema(description = "회원가입 유무")
        boolean isSignUp
) {

}
