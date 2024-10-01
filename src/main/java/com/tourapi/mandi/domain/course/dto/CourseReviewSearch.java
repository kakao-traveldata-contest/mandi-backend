package com.tourapi.mandi.domain.course.dto;

import static java.lang.Math.min;

import com.tourapi.mandi.domain.course.util.CourseReviewSortType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.Optional;
import lombok.Builder;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ParameterObject
public record CourseReviewSearch(
        @Schema(description = "페이지 번호 (default: 1)")
        @Min(value = 1)
        Integer page,

        @Schema(description = "페이지 크기 (default: 10)")
        @Min(value = 1)
        @Max(value = MAX_SIZE)
        Integer size,

        @Schema(description = "정렬 기준 (LATEST: 최신 등록순, HIGHEST_RATING: 평점 높은순, LOWEST_RATING: 평점 낮은순)")
        CourseReviewSortType sortType
) {
        private static final int DEFAULT_PAGE = 1;
        private static final int DEFAULT_SIZE = 10;
        private static final int MAX_SIZE = 100;
        private static final CourseReviewSortType DEFAULT_SORT_TYPE = CourseReviewSortType.LATEST;

        @Builder
        public CourseReviewSearch(Integer page, Integer size, CourseReviewSortType sortType) {
                this.page = Optional.ofNullable(page).orElse(DEFAULT_PAGE);
                this.size = Optional.ofNullable(size).orElse(DEFAULT_SIZE);
                this.sortType = Optional.ofNullable(sortType).orElse(DEFAULT_SORT_TYPE);
        }

        public long getOffset() {
                return (long) (page - 1) * min(size, MAX_SIZE);
        }

        public Pageable getPageable() {
                return PageRequest.of(page - 1, size);
        }
}
