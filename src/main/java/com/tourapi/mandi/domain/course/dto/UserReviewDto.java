package com.tourapi.mandi.domain.course.dto;

import com.tourapi.mandi.domain.post.dto.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record UserReviewDto(
        @Schema(description = "후기 작성자 정보")
        UserDto user,

        @Schema(description = "후기 정보")
        ReviewDto review
) {
}
