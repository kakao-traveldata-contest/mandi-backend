package com.tourapi.mandi.domain.badge;

import com.tourapi.mandi.global.exception.BaseExceptionStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BadgeExceptionStatus implements BaseExceptionStatus {

	BADGE_NOT_FOUND("해당하는 배지가 없습니다.", 404, "24040");

	@Getter
	private final String message;
	@Getter
	private final int status;
	@Getter
	private final String errorCode;

}
