package com.tourapi.mandi.domain.course.controller;

import com.tourapi.mandi.domain.course.dto.CompletedCourseListResponseDto;
import com.tourapi.mandi.domain.course.dto.CourseListResponseDto;
import com.tourapi.mandi.domain.course.dto.CourseNameResponseDto;
import com.tourapi.mandi.domain.course.dto.CourseNearbyListResponseDto;
import com.tourapi.mandi.domain.course.dto.CourseNearbyRequestDto;
import com.tourapi.mandi.domain.course.dto.CoursePreferredListResponseDto;
import com.tourapi.mandi.domain.course.dto.CourseResponseDto;
import com.tourapi.mandi.domain.course.dto.CourseReviewListResponseDto;
import com.tourapi.mandi.domain.course.dto.CourseReviewSearch;
import com.tourapi.mandi.domain.course.dto.CourseSearchDto;
import com.tourapi.mandi.domain.course.service.CompletedCourseService;
import com.tourapi.mandi.domain.course.service.CourseService;
import com.tourapi.mandi.global.security.CustomUserDetails;
import com.tourapi.mandi.global.util.ApiUtils;
import com.tourapi.mandi.global.util.ApiUtils.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "코스 API 목록")
@Slf4j
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
        log.info("코스 목록 조회 호출 | parameter = {}", courseSearch);
        return ResponseEntity.ok(ApiUtils.success(courseService.findBySearch(courseSearch)));
    }

    @Operation(summary = "상세 코스 조회")
    @ApiResponse(responseCode = "200", description = "코스 조회 성공")
    @GetMapping("/{courseId}")
    public ResponseEntity<ApiResult<CourseResponseDto>> findCourse(@PathVariable Long courseId) {
        log.info("상세 코스 조회 호출 | parameter = {}", courseId);
        return ResponseEntity.ok(ApiUtils.success(courseService.getCourseById(courseId)));
    }

    @Operation(summary = "코스 후기 목록 조회")
    @ApiResponse(responseCode = "200", description = "코스 후기 목록 조회 성공")
    @GetMapping("/{courseId}/reviews")
    public ResponseEntity<ApiResult<CourseReviewListResponseDto>> findCourseReviews(
            @PathVariable Long courseId,
            @ModelAttribute CourseReviewSearch courseReviewSearch
    ) {
        log.info("코스 후기 목록 조회 호출 | parameter = {}, {}", courseId, courseReviewSearch);
        return ResponseEntity.ok(ApiUtils.success(completedCourseService.getReviewsByCourseId(
                courseId,
                courseReviewSearch
        )));
    }

    @Operation(summary = "코스 이름 목록 조회")
    @ApiResponse(responseCode = "200", description = "코스 이름 목록 조회 성공")
    @GetMapping("/names")
    public ResponseEntity<ApiResult<List<CourseNameResponseDto>>> getNames() {
        log.info("코스 이름 목록 조회 호출");
        return ResponseEntity.ok(ApiUtils.success(courseService.getNames()));
    }

    @Operation(summary = "주변 코스 목록 조회")
    @ApiResponse(responseCode = "200", description = "코스 목록 조회 성공")
    @PostMapping("/nearby")
    public ResponseEntity<ApiResult<CourseNearbyListResponseDto>> getNearbyCourses(
            @RequestBody CourseNearbyRequestDto courseNearbyRequestDto
    ) {
        log.info("주변 코스 목록 조회 호출 | parameter = {}", courseNearbyRequestDto);
        return ResponseEntity.ok(ApiUtils.success(courseService.findCoursesInBound(courseNearbyRequestDto)));
    }

    @Operation(summary = "완주한 코스 목록 조회")
    @ApiResponse(responseCode = "200", description = "완주한 코스 목록 조회 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자 요청 에러")
    @GetMapping("/completed")
    public ResponseEntity<ApiResult<CompletedCourseListResponseDto>> getCompletedCourses(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        log.info("완주한 코스 목록 조회 호출");
        return ResponseEntity.ok(ApiUtils.success(completedCourseService.getCompletedCourses(userDetails.user())));
    }

    @Operation(summary = "추천 코스 목록 조회")
    @ApiResponse(responseCode = "200", description = "추천 코스 목록 조회 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자 요청 에러")
    @GetMapping("/preferred")
    public ResponseEntity<ApiResult<CoursePreferredListResponseDto>> getPreferredCourses(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        log.info("추천 코스 목록 조회 호출");
        return ResponseEntity.ok(ApiUtils.success(courseService.findPreferredCourses(userDetails.user())));
    }
}
