package com.tourapi.mandi.domain.comment.service;


import com.tourapi.mandi.domain.comment.CommentExceptionStatus;
import com.tourapi.mandi.domain.comment.entity.Comment;
import com.tourapi.mandi.domain.comment.repository.CommentRepository;
import com.tourapi.mandi.domain.post.PostExceptionStatus;
import com.tourapi.mandi.domain.post.entity.Post;
import com.tourapi.mandi.domain.user.UserExceptionStatus;
import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.domain.user.repository.UserJpaRepository;
import com.tourapi.mandi.global.exception.Exception404;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {


    private final CommentRepository commentRepository;
    private final UserJpaRepository userJpaRepository;

    // 좋아요 추가
    public boolean addLike(Long commentId, User user) {
        // 게시글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new Exception404(CommentExceptionStatus.COMMENT_NOT_FOUND));


        //유저만 좋아요 가능
        userJpaRepository.findById(user.getUserId())
                .orElseThrow(() -> new Exception404(UserExceptionStatus.USER_NOT_FOUND));

        // 좋아요 수 증가
        comment.setLikeCnt(comment.getLikeCnt() + 1);


        return true;
    }

    // 좋아요 삭제
    public boolean removeLike(Long commentId, User user) {
        // 게시글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new Exception404(CommentExceptionStatus.COMMENT_NOT_FOUND));

        //유저만 좋아요 가능
        userJpaRepository.findById(user.getUserId())
                .orElseThrow(() -> new Exception404(UserExceptionStatus.USER_NOT_FOUND));


        // 좋아요 수 감소
        comment.setLikeCnt(comment.getLikeCnt() - 1);


        return true;
    }
}
