package com.tourapi.mandi.domain.post.dto;

import com.tourapi.mandi.domain.post.entity.Category;
import com.tourapi.mandi.domain.post.entity.PostImage;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreatePostRequestDto(
        @NotNull(message = "카테고리는 필수 입력 사항입니다.")
        Category category,
        String content,
        @NotNull(message = "제목은 필수 입력 사항입니다.")
        @Size(max = 150, message = "제목은 최대 150자 입니다.")
        String title,
        List<String> Base64EncodedImageList
) {}
