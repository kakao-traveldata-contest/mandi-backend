package com.tourapi.mandi.domain.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;

public record CreateReviewRequestDto(
        @Pattern(regexp = "^(?!\\s*$).+", message = "후기 내용은 공백 이외의 문자로 입력해야 합니다.")
        @Schema(description = "후기 내용")
        String content,

        @NotNull(message = "후기 작성 시 별점은 필수 입력 값입니다.")
        @Max(value = 5, message = "후기 별점은 5 이하여야 합니다.")
        @Schema(description = "후기 별점")
        Integer score,

        @Schema(description = "이미지 url 목록")
        List<String> base64EncodedImageList
) {

}
