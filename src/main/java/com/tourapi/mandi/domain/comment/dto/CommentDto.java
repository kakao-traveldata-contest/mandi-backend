package com.tourapi.mandi.domain.comment.dto;

import java.util.List;

public record CommentDto(
        Long commentId,
        CommentDto parentComment,
        List<CommentDto> childComments,
        String content,
        int likeCnt
) {

}
