package com.tourapi.mandi.domain.user.dto;

import com.tourapi.mandi.domain.user.UserExceptionStatus;
import com.tourapi.mandi.domain.user.entity.constant.Provider;
import com.tourapi.mandi.global.exception.Exception400;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "프로필 변경 요청 DTO")
public record ProfileInfoResponseDto(
        @Schema(description = "닉네임", nullable = true)
        String nickname,

        @Schema(description = "프로필이미지 링크", nullable = true)
        String imgUrl,

        @Schema(description = "한줄소개", nullable = true)
        String description,

        @Schema(description = "리뷰 총합", nullable = true)
        int totalReviews,

        @Schema(description = "완조 코스 개수", nullable = true)
        int completedCourses,

        @Schema(description = "이메일", nullable = true)
        String email,

        @Schema(description = "소셜 로그인 플랫폼", nullable = true)
        Provider provider



) {

        }
