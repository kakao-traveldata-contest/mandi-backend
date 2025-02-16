package com.tourapi.mandi.domain.comment.util;


import com.tourapi.mandi.domain.comment.dto.CommentDto;
import com.tourapi.mandi.domain.comment.dto.CommentRequestDto;
import com.tourapi.mandi.domain.comment.entity.Comment;
import com.tourapi.mandi.domain.post.entity.Post;
import com.tourapi.mandi.domain.post.util.PostMapper;
import com.tourapi.mandi.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {


    public static Comment toCommentFromCreateCommentRequestDto(CommentRequestDto createCommentRequestDto, Post post, User user, Comment parentComment) {
        return Comment.builder()
                .post(post) // Post 객체
                .parentComment(parentComment) // 부모 댓글 (없으면 null)
                .user(user) // User 객체
                .content(createCommentRequestDto.getContent()) // 댓글 내용
                .likeCnt(0) // 초기 좋아요 수
                .isDeleted(false) // 삭제 여부
                .build();
    }

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .commentId(comment.getCommentId())  // 댓글 ID
                .parentCommentId(comment.getParentComment() != null ? comment.getParentComment().getCommentId() : null)
                .content(comment.getContent())  // 댓글 내용
                .likeCnt(comment.getLikeCnt())  // 좋아요 수
                .user(PostMapper.toUserDto(comment.getUser()))
                .uploadDate(comment.getCreatedAt())
                .isDeleted(comment.isDeleted())
                .childComments(comment.getChildComments().stream()
                        .map(CommentMapper::toCommentDto)
                        .toList())  // 자식 댓글 리스트
                .build();
    }

}
