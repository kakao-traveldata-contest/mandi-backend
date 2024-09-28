package com.tourapi.mandi.domain.course.dto;

import com.tourapi.mandi.domain.course.entity.Coordinate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "좌표 기반 영역 검색 요청 DTO")
public record CourseNearbyRequestDto(

        @NotNull(message = "좌표는 필수 입력 사항입니다.")
        @Schema(description = "남서쪽 좌표")
        Coordinate sw,

        @NotNull(message = "좌표는 필수 입력 사항입니다.")
        @Schema(description = "북동쪽 좌표")
        Coordinate ne
) {
}
