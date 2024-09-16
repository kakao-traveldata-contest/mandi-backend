package com.tourapi.mandi.domain.course.dto;

import com.tourapi.mandi.global.common.PageInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
public record CourseListResponseDto(
        @Schema(description = "페이지 정보")
        PageInfoDto pageInfo,

        @Schema(description = "코스 목록")
        List<CourseResponseDto> courses
) {
}
