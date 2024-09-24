package com.tourapi.mandi.domain.comment.dto;

import com.tourapi.mandi.domain.post.dto.UserDto;
import lombok.Builder;

import java.util.List;

@Builder
public record CommentDto(
        Long commentId,
        Long parentCommentId,
        List<CommentDto> childComments,
        UserDto userDto,
        String uploadDate,
        String imgUrl,
        String content,
        int likeCnt
) {

}
