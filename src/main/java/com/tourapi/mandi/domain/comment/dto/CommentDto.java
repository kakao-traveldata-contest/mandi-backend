package com.tourapi.mandi.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tourapi.mandi.domain.post.dto.UserDto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CommentDto(
        Long commentId,
        Long parentCommentId,
        List<CommentDto> childComments,
        UserDto user,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        LocalDateTime uploadDate,
        String content,
        boolean isDeleted,
        int likeCnt
) {

}
