package com.tourapi.mandi.domain.post.dto;

import com.tourapi.mandi.domain.post.entity.Category;
import com.tourapi.mandi.domain.post.entity.Post;
import lombok.Builder;

import java.util.List;

@Builder
public record PostDto(
        Long postId,
        UserDto user,
        Category category,
        String content,
        String title,
        List<PostImageDto> listPostImg
) {

}
