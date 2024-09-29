package com.tourapi.mandi.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentRequestDto {
    private Long parentCommentId;
    private String content;

}
