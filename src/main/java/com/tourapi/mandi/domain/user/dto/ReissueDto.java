package com.tourapi.mandi.domain.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;


public class ReissueDto {

    public record ReissueRequestDto(
            @NotNull(message = "Refresh 토큰을 입력해주세요.")
            String refreshToken
    ) {}
    @Builder
    public record ReissueResponseDto(
            String accessToken,
            String refreshToken
    ) {}
}
