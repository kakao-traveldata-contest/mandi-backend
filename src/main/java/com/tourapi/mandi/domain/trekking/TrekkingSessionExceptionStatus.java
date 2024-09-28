package com.tourapi.mandi.domain.trekking;

import com.tourapi.mandi.global.exception.BaseExceptionStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TrekkingSessionExceptionStatus implements BaseExceptionStatus {

	TREKKING_NOT_FOUND("해당하는 트레킹 이력이 없습니다.", 404, "24040");

	@Getter
	private final String message;
	@Getter
	private final int status;
	@Getter
	private final String errorCode;

}
