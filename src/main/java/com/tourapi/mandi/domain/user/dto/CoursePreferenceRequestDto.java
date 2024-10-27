package com.tourapi.mandi.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record CoursePreferenceRequestDto(

        @NotNull(message = "코스 난이도 정보를 입력해 주세요.")
        @Range(min = 1, max = 3, message = "코스 난이도 정보는 1~3 사이의 값을 입력해 주세요.")
        @Schema(description = "코스 난이도 (1: Beginner, 2: Intermediate, 3: Advanced)")
        Integer difficultyLevel,

        @NotNull(message = "거리 난이도 정보를 입력해 주세요.")
        @Range(min = 1, max = 4, message = "거리 난이도 정보는 1~4 사이의 값을 입력해 주세요.")
        @Schema(description = "거리 난이도 (1: less than 3 hours\n2: 3 to 6 hours\n3: 6 to 9 hours\n4: more than 9 hours)")
        Integer durationLevel,

        @NotNull(message = "코스 주변 환경 정보를 입력해 주세요.")
        @Range(min = 1, max = 3, message = "코스 주변 환경 유형은 1~3 사이의 값을 입력해 주세요.")
        @Schema(description = "코스 주변 선호 환경 (1: Ocean, 2: Mountain, 3: Doesn't matter")
        Integer environmentLevel
) {
}
