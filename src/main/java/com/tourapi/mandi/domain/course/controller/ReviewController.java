package com.tourapi.mandi.domain.course.controller;

import com.tourapi.mandi.domain.course.dto.ReviewCreateRequestDto;
import com.tourapi.mandi.domain.course.dto.ReviewDto;
import com.tourapi.mandi.domain.course.dto.ReviewListResponseDto;
import com.tourapi.mandi.domain.course.dto.ReviewUpdateRequestDto;
import com.tourapi.mandi.domain.course.service.CompletedCourseService;
import com.tourapi.mandi.global.security.CustomUserDetails;
import com.tourapi.mandi.global.util.ApiUtils;
import com.tourapi.mandi.global.util.ApiUtils.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "코스 완주 및 후기 API 목록")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final CompletedCourseService completedCourseService;

    @Operation(summary = "후기 목록 조회")
    @ApiResponse(responseCode = "200", description = "완주 코스 후기 목록 조회 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자 요청 에러")
    @GetMapping
    public ResponseEntity<ApiResult<ReviewListResponseDto>> getReviews(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        ReviewListResponseDto responseDto = completedCourseService.getReviews(userDetails.user());

        return ResponseEntity.ok(ApiUtils.success(responseDto));
    }

    @Operation(summary = "후기 상세 조회 및 수정 가능 여부 확인")
    @ApiResponse(responseCode = "200", description = "후기 조회 성공 & 후기 수정 가능")
    @ApiResponse(responseCode = "403", description = "권한 없는 사용자 요청 에러")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 후기 요청 에러")
    @GetMapping("/{completedCourseId}")
    public ResponseEntity<ApiResult<ReviewDto>> getReview(
            @PathVariable Long completedCourseId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        ReviewDto responseDto = completedCourseService.getReview(userDetails.user(), completedCourseId);

        return ResponseEntity.ok(ApiUtils.success(responseDto));
    }

    @Operation(summary = "후기 등록")
    @ApiResponse(responseCode = "200", description = "후기 등록 성공")
    @ApiResponse(responseCode = "400", description = "S3 이미지 생성 에러")
    @ApiResponse(responseCode = "403", description = "권한 없는 사용자 요청 에러")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자 요청 에러")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 코스 완주 기록 에러")
    @ApiResponse(responseCode = "409", description = "후기 중복 등록 요청 에러")
    @PostMapping
    public ResponseEntity<ApiResult<ReviewDto>> createReview(
            @Valid @RequestBody ReviewCreateRequestDto reviewCreateRequestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        ReviewDto reviewDto = completedCourseService.createReview(
                userDetails.user(),
                reviewCreateRequestDto
        );

        return ResponseEntity.ok(ApiUtils.success(reviewDto));
    }

    @Operation(summary = "후기 삭제")
    @ApiResponse(responseCode = "200", description = "후기 삭제 성공")
    @ApiResponse(responseCode = "403", description = "권한 없는 사용자 요청 에러")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 코스 완주 기록 에러")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 후기 에러")
    @ApiResponse(responseCode = "500", description = "S3 이미지 삭제 에러")
    @DeleteMapping("/{completedCourseId}")
    public ResponseEntity<ApiResult<Boolean>> deleteReview(
            @PathVariable Long completedCourseId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(ApiUtils.success(completedCourseService.deleteReview(
                completedCourseId,
                userDetails.user()
        )));
    }

    @Operation(summary = "후기 수정")
    @ApiResponse(responseCode = "200", description = "후기 수정 성공")
    @ApiResponse(responseCode = "400", description = "S3 이미지 생성 에러")
    @ApiResponse(responseCode = "403", description = "권한 없는 사용자 요청 에러")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 코스 완주 기록 에러")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 후기 에러")
    @ApiResponse(responseCode = "500", description = "S3 이미지 삭제 에러")
    @PatchMapping("/{completedCourseId}")
    public ResponseEntity<ApiResult<ReviewDto>> updateReview(
            @PathVariable Long completedCourseId,
            @Valid @RequestBody ReviewUpdateRequestDto reviewUpdateRequestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(ApiUtils.success(completedCourseService.updateReview(
                completedCourseId,
                userDetails.user(),
                reviewUpdateRequestDto
        )));
    }
}
