package com.tourapi.mandi.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateCommentRequestDto {
    private Long parentCommentId;
    private String content;

}
