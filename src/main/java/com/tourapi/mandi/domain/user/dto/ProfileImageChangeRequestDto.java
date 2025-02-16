package com.tourapi.mandi.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


@Schema(description = "프로필 사진 변경 요청 DTO")
public record ProfileImageChangeRequestDto (

        @Schema(description = "Base64로 인코딩된 사진데이터")
        @NotBlank(message = "Base64EncodedImage필드값이 없습니다.")
        String Base64EncodedImage

) {

}