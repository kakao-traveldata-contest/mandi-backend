package com.tourapi.mandi.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "회원 프로필 정보 DTO")
public record UserProfileDto(
        @Schema(description = "닉네임")
        String nickname,

        @Schema(description = "한줄소개")
        String description,

        @Schema(description = "프로필 이미지 URL")
        String imageUrl
) {
}
