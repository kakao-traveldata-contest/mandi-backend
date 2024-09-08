package com.tourapi.mandi.domain.user.dto;

import jakarta.validation.constraints.NotNull;

public class ReissueDto {

    public record ReissueRequestDto(
            @NotNull(message = "Refresh 토큰을 입력해주세요.")
            String refreshToken
    ) {}

    public record ReissueResponseDto(
            String accessToken,
            String refreshToken
    ) {
        public static ReissueResponseDto of(String accessToken, String refreshToken) {
            return new ReissueResponseDto(accessToken, refreshToken);
        }
    }
}
