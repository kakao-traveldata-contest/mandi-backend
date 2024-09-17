package com.tourapi.mandi.domain.post.dto;

import com.tourapi.mandi.domain.post.entity.Post;
import com.tourapi.mandi.global.dto.PageInfoDto;

import java.util.List;

public record PostsByCategoryResponseDto(
        List<Post> posts,
        PageInfoDto pageInfo
) {

    public PostsByCategoryResponseDto(List<Post> posts, PageInfoDto pageInfo) {
        this.posts = posts;
        this.pageInfo = pageInfo;
    }
}
