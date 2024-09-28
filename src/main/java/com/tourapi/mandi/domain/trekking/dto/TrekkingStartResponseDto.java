package com.tourapi.mandi.domain.trekking.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record TrekkingStartResponseDto(

	@Schema(description = "트레킹 가능 여부")
	Boolean enabled
) {
}
