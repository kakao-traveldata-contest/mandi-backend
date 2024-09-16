package com.tourapi.mandi.global.common;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

public record PageInfoDto(
        @Schema(description = "전체 요소 개수")
        long totalCount,

        @Schema(description = "한 페이지 결과 개수")
        int size,

        @Schema(description = "현재 페이지 번호")
        int currentPage,

        @Schema(description = "전체 페이지 개수")
        int totalPages
) {
    public PageInfoDto(Page<?> page) {
        this(
                page.getTotalElements(),
                page.getSize(),
                page.getPageable().getPageNumber() + 1,
                page.getTotalPages()
        );
    }
}
