package com.tourapi.mandi.domain.course.dto;

import static java.lang.Math.max;
import static java.lang.Math.min;

import com.tourapi.mandi.domain.course.entity.DifficultyLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ParameterObject
public record CourseSearchDto(
        @Schema(description = "페이지 번호 (default: 1)")
        Integer page,

        @Schema(description = "페이지 크기 (default: 10)")
        Integer size,

        @Schema(description = "코스 평점 (default: 0)")
        Integer rating,

        @Schema(description = "검색 키워드")
        String keyword,

        @Schema(description = "거리 정렬 기준 (ASC: 오름차순/DESC: 내림차순)")
        String orderByDirection,

        @Schema(description = "난이도 (1: Easy, 2: Moderate, 3: Hard)")
        List<Integer> difficulties
) {
    private static final int MAX_SIZE = 100;

    @Builder
    public CourseSearchDto(
            Integer page,
            Integer size,
            Integer rating,
            String keyword,
            String orderByDirection,
            List<Integer> difficulties
    ) {
        this.page = page == null ? 1 : max(1, page);
        this.size = size == null ? 10 : max(1, size);
        this.keyword = keyword;
        this.orderByDirection = Objects.requireNonNullElse(orderByDirection, "ASC");
        this.rating = rating;
        this.difficulties = difficulties;
    }

    public long getOffset() {
        return (long) (page - 1) * min(size, MAX_SIZE);
    }

    public Pageable getPageable() {
        return PageRequest.of(page - 1, size);
    }

    public List<DifficultyLevel> getDifficultyKeywords() {
        return difficulties.stream().map(DifficultyLevel::of).toList();
    }
}
