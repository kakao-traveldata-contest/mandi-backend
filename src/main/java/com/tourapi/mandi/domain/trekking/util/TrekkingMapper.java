package com.tourapi.mandi.domain.trekking.util;

import com.tourapi.mandi.domain.trekking.dto.TrekkingStartResponseDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TrekkingMapper {

	public static TrekkingStartResponseDto mapToTrekkingStartSuccessResponseDto() {
		return new TrekkingStartResponseDto(true);
	}

	public static TrekkingStartResponseDto mapToTrekkingStartFailResponseDto() {
		return new TrekkingStartResponseDto(false);
	}
}
