package com.tourapi.mandi.domain.post.dto;

import com.tourapi.mandi.domain.comment.dto.CommentDto;
import com.tourapi.mandi.domain.post.entity.Category;
import lombok.Builder;

import java.util.List;

@Builder
public record DetailPostDto(
        Long postId,
        UserDto user,
        Category category,
        String content,
        String title,
        int likeCnt,
        List<PostImageDto> imgUrlList,
        List<CommentDto> commentList  // 댓글 리스트 추가
) {

}
