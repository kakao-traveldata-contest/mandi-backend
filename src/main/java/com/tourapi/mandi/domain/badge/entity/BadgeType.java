package com.tourapi.mandi.domain.badge.entity;

import lombok.Getter;

@Getter
public enum BadgeType {
	
	MANDI_STARTER(1L),
	COURSE_COLLECTOR(2L),
	JOY_OF_SHARING(3L),
	BEGINNING_OF_COMPLETION(4L),
	WALKED_10000_STEPS(5L),
	MANDI_HOLIC(6L);
	
	private final Long id;
	
	BadgeType(Long id) {
		this.id = id;
	}
}
