package com.tourapi.mandi.domain.trekking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tourapi.mandi.domain.trekking.dto.TrekkingStartRequestDto;
import com.tourapi.mandi.domain.trekking.dto.TrekkingStartResponseDto;
import com.tourapi.mandi.domain.trekking.service.TrekkingService;
import com.tourapi.mandi.global.security.CustomUserDetails;
import com.tourapi.mandi.global.util.ApiUtils;
import com.tourapi.mandi.global.util.ApiUtils.ApiResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "트레킹 API 목록")
@RestController
@RequiredArgsConstructor
@RequestMapping("/trekking")
public class TrekkingController {

	private final TrekkingService trekkingService;

	@Operation(summary = "트레킹 시작 여부 조회")
	@PostMapping("/{courseId}/start")
	public ResponseEntity<ApiResult<TrekkingStartResponseDto>> startTrekking(
		@PathVariable Long courseId,
		@RequestBody TrekkingStartRequestDto request,
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(
			ApiUtils.success(trekkingService.findTrekkingStatus(userDetails.user(), courseId, request.userLocation()))
		);
	}
}
