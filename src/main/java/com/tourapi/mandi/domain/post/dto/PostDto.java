package com.tourapi.mandi.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tourapi.mandi.domain.post.entity.Category;
import com.tourapi.mandi.domain.post.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PostDto(
        Long postId,
        UserDto user,
        Category category,
        String content,
        String title,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        LocalDateTime uploadDate,
        int likeCnt,
        int commentCnt,
        List<PostImageDto> imgUrlList
) {

}
