package com.tourapi.mandi.domain.post.util;

import com.tourapi.mandi.domain.comment.dto.CommentDto;
import com.tourapi.mandi.domain.comment.entity.Comment;
import com.tourapi.mandi.domain.comment.util.CommentMapper;
import com.tourapi.mandi.domain.post.dto.*;
import com.tourapi.mandi.domain.post.entity.Category;
import com.tourapi.mandi.domain.post.entity.Post;
import com.tourapi.mandi.domain.post.entity.PostImage;
import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.global.dto.PageInfoDto;
import com.tourapi.mandi.global.util.S3ImageClient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PostMapper {


    public static PostDto toPostDto(Post post) {


        // 부모 댓글 리스트
        List<CommentDto> parentComments = post.getCommentList().stream()
                .filter(c -> c.getParentComment() == null)  // 부모 댓글만 포함
                .map(CommentMapper::toCommentDto)
                .toList();


        // 삭제되지 않은 댓글의 총 개수 계산
        int totalCommentCount = post.getCommentList().stream()
                .filter(comment -> !comment.isDeleted())  // isDelete가 false인 댓글만 카운트
                .toList().size();


        return PostDto.builder()
                .postId(post.getPostId())
                .user(toUserDto(post.getUser()))
                .category(post.getCategory())
                .content(post.getContent())
                .title(post.getTitle())
                .uploadDate(post.getCreatedAt())
                .likeCnt(post.getLikeCnt())
                .commentCnt(totalCommentCount)
                .imgUrlList(post.getPostImageList().stream().map(PostMapper::toPostImageDto).toList())
                .build();
    }

    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .imgUrl(user.getImgUrl())
                .build();
    }

    public static PostImageDto toPostImageDto(PostImage postImage) {
        return PostImageDto.builder()
                .url(postImage.getUrl())
                .build();
    }


    public static PostsByCategoryResponseDto toPostsByCategoryResponseDto(Page<PostDto> postPage) {
        PageInfoDto pageInfo = new PageInfoDto(postPage);
        return PostsByCategoryResponseDto.builder()
                .posts(postPage.getContent())
                .pageInfo(pageInfo)
                .build();
    }

    public static PostImage toPostImageFromUrl(String url, Post post) {
        return PostImage.builder()
                .post(post)  // 생성된 Post 객체를 할당
                .url(url)
                .build();
    }


    public static Post toPostFromCreatePostRequestDto(User user, CreatePostRequestDto createPostRequestDto, S3ImageClient s3ImageClient) {

        // CreatePostRequestDto에서 값 추출
        List<String> base64EncodedImageList = createPostRequestDto.Base64EncodedImageList();

        // 이미지 목록을 S3에 업로드하고 URL 리스트를 생성
        List<String> imgUrlList = base64EncodedImageList.stream()
                .map(s3ImageClient::base64ImageToS3)  // base64 값을 해독해 S3에 업로드 후 URL 반환
                .toList();

        // Post 객체 먼저 생성( 순서중요!!)
        Post post = Post.builder()
                .user(user)
                .category(createPostRequestDto.category())
                .content(createPostRequestDto.content())
                .title(createPostRequestDto.title())
                .likeCnt(0)
                .commentList(new HashSet<>())
                .build();

        // 이미지 목록을 S3에 업로드하고, URL 리스트를 생성한 후 PostImage로 변환 (Post 객체 전달)
        List<PostImage> postImageList = imgUrlList.stream()
                .map(url -> PostMapper.toPostImageFromUrl(url, post))
                .collect(Collectors.toList());

        // Post에 이미지 리스트를 설정
        post.setPostImageList(postImageList);

        return post;
    }



    public static DetailPostDto toDetailPostDto(Post post) {





        // 부모 댓글 리스트
        List<CommentDto> parentComments = post.getCommentList().stream()
                .filter(c -> c.getParentComment() == null)  // 부모 댓글만 포함
                .map(CommentMapper::toCommentDto)
                .toList();

        // 삭제되지 않은 댓글의 총 개수 계산
        int totalCommentCount = post.getCommentList().stream()
                .filter(comment -> !comment.isDeleted())  // isDelete가 false인 댓글만 카운트
                .toList().size();


        return DetailPostDto.builder()
                .postId(post.getPostId())
                .user(toUserDto(post.getUser()))
                .category(post.getCategory())
                .likeCnt(post.getLikeCnt())
                .content(post.getContent())
                .title(post.getTitle())
                .imgUrlList(post.getPostImageList().stream()
                        .map(PostMapper::toPostImageDto)
                        .toList())
                .commentList(parentComments)
                .CommentCnt(totalCommentCount)
                .uploadDate(post.getCreatedAt())
                .build();
    }







}
