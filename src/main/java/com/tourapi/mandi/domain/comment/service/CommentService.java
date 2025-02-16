package com.tourapi.mandi.domain.comment.service;


import com.tourapi.mandi.domain.comment.CommentExceptionStatus;
import com.tourapi.mandi.domain.comment.dto.CommentDto;
import com.tourapi.mandi.domain.comment.dto.CommentRequestDto;
import com.tourapi.mandi.domain.comment.entity.Comment;
import com.tourapi.mandi.domain.comment.repository.CommentRepository;
import com.tourapi.mandi.domain.comment.util.CommentMapper;
import com.tourapi.mandi.domain.post.PostExceptionStatus;
import com.tourapi.mandi.domain.post.entity.Post;
import com.tourapi.mandi.domain.post.repository.PostRepository;
import com.tourapi.mandi.domain.post.util.PostMapper;
import com.tourapi.mandi.domain.user.UserExceptionStatus;
import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.domain.user.repository.UserJpaRepository;
import com.tourapi.mandi.global.exception.Exception403;
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
    private final PostRepository postRepository;



    public CommentDto createComment(CommentRequestDto commentRequestDto, Long id, User user) {



        // User 검증: 사용자 존재 여부 확인
        User existingUser = userJpaRepository.findById(user.getUserId()).orElseThrow(
                () -> new Exception404(UserExceptionStatus.USER_NOT_FOUND)
        );


        CommentDto commentDto;

        //createCommentRequestDto가 null일경우
        if(commentRequestDto.getParentCommentId()==null){

            Post post = postRepository.findById(id).orElseThrow(
                    () -> new Exception404(PostExceptionStatus.POST_NOT_FOUND)
            );

            Comment comment=CommentMapper.toCommentFromCreateCommentRequestDto(commentRequestDto,post,user,null);


            commentRepository.save(comment);

             commentDto = CommentMapper.toCommentDto(comment);
        }

        //createCommentRequestDto가 null이 아닐경우
        else{
            Comment existingComment = commentRepository.findById(id).orElseThrow(
                    () -> new Exception404(CommentExceptionStatus.COMMENT_NOT_FOUND)
            );
            Comment comment=CommentMapper.toCommentFromCreateCommentRequestDto(commentRequestDto,existingComment.getPost(),user,existingComment);
            commentRepository.save(comment);

             commentDto = CommentMapper.toCommentDto(comment);

        }

        return commentDto;
    }

    public Boolean deleteComment(Long id, User user) {

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new Exception404(CommentExceptionStatus.COMMENT_NOT_FOUND));


        // 지우려는 댓글의 유저와 요청한 사용자가 일치하는지 확인
        if (!comment.getUser().getUserId().equals(user.getUserId())) {
            throw new Exception403(UserExceptionStatus.USER_NOT_AUTHORIZED);
        }

        comment.setDeleted(true);


        return true;
    }


    public CommentDto updateComment(CommentRequestDto commentRequestDto,Long id, User user) {


        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new Exception404(CommentExceptionStatus.COMMENT_NOT_FOUND));


        // 변경하려는 댓글의 유저와 요청한 사용자가 일치하는지 확인
        if (!comment.getUser().getUserId().equals(user.getUserId())) {
            throw new Exception403(UserExceptionStatus.USER_NOT_AUTHORIZED);
        }

        comment.setContent(commentRequestDto.getContent());


        return CommentMapper.toCommentDto(comment);
    }


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
