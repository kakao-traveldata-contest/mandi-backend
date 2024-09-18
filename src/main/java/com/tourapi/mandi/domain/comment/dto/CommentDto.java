package com.tourapi.mandi.domain.comment.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CommentDto(
        Long commentId,
        Long parentCommentId,
        List<CommentDto> childComments,
        String content,
        int likeCnt
) {

}
