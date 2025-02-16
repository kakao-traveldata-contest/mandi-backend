package com.tourapi.mandi.domain.course.dto;

import java.math.BigDecimal;
import com.tourapi.mandi.domain.course.entity.DifficultyLevel;
import com.tourapi.mandi.domain.course.entity.Location;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CourseResponseDto(
	@Schema(description = "코스 id")
	Long id,

	@Schema(description = "코스 이름")
	String courseName,

	@Schema(description = "코스 거리")
	BigDecimal distance,

	@Schema(description = "코스 시작 지점 정보")
	Location startPoint,

	@Schema(description = "코스 끝 지점 정보")
	Location endPoint,

	@Schema(description = "코스 난이도")
	DifficultyLevel difficulty,

	@Schema(description = "코스 평점")
	BigDecimal ratingAverage,

	@Schema(description = "코스 소요 시간")
	String duration,

	@Schema(description = "코스 이미지 url")
	String imgUrl,

	@Schema(description = "코스 경로 url")
	String gpxUrl
) {
}
