package com.tourapi.mandi.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tourapi.mandi.domain.comment.dto.CommentDto;
import com.tourapi.mandi.domain.post.entity.Category;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record DetailPostDto(
        Long postId,
        UserDto user,
        Category category,
        String content,
        String title,
        int likeCnt,
        int CommentCnt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        LocalDateTime uploadDate,
        List<PostImageDto> imgUrlList,
        List<CommentDto> commentList  // 댓글 리스트 추가,

) {

}
