package com.tourapi.mandi.domain.course.controller;

import com.tourapi.mandi.domain.course.dto.CompletedCourseListResponseDto;
import com.tourapi.mandi.domain.course.dto.CourseListResponseDto;
import com.tourapi.mandi.domain.course.dto.CourseSearchDto;
import com.tourapi.mandi.domain.course.dto.ReviewListResponseDto;
import com.tourapi.mandi.domain.course.service.CompletedCourseService;
import com.tourapi.mandi.domain.course.service.CourseService;
import com.tourapi.mandi.global.security.CustomUserDetails;
import com.tourapi.mandi.global.util.ApiUtils;
import com.tourapi.mandi.global.util.ApiUtils.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "코스 API 목록")
@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final CompletedCourseService completedCourseService;

    @Operation(summary = "코스 목록 조회")
    @ApiResponse(responseCode = "200", description = "코스 목록 조회 성공")
    @GetMapping
    public ResponseEntity<ApiResult<CourseListResponseDto>> findCourses(
            @ModelAttribute CourseSearchDto courseSearch
    ) {
        return ResponseEntity.ok(ApiUtils.success(courseService.findBySearch(courseSearch)));
    }

    @Operation(summary = "완주한 코스 목록 조회")
    @ApiResponse(responseCode = "200", description = "완주한 코스 목록 조회 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자 요청 에러")
    @GetMapping("/completed")
    public ResponseEntity<ApiResult<CompletedCourseListResponseDto>> getCompletedCourses(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        CompletedCourseListResponseDto responseDto = completedCourseService.getCompletedCourses(userDetails.user());

        return ResponseEntity.ok(ApiUtils.success(responseDto));
    }

    @Operation(summary = "완주 코스 후기 목록 조회")
    @ApiResponse(responseCode = "200", description = "완주 코스 후기 목록 조회 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자 요청 에러")
    @GetMapping("/completed/reviews")
    public ResponseEntity<ApiResult<ReviewListResponseDto>> getReviews(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        ReviewListResponseDto responseDto = completedCourseService.getReviews(userDetails.user());

        return ResponseEntity.ok(ApiUtils.success(responseDto));
    }
}
