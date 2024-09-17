package com.tourapi.mandi.domain.post.dto;

import com.tourapi.mandi.global.dto.PageInfoDto;
import lombok.Builder;

import java.util.List;
@Builder
public record PostsByCategoryResponseDto(
        List<PostDto> posts,
        PageInfoDto pageInfo
) {}
