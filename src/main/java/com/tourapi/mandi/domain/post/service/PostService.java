package com.tourapi.mandi.domain.post.service;


import com.tourapi.mandi.domain.post.PostExceptionStatus;
import com.tourapi.mandi.domain.post.dto.CreatePostRequestDto;
import com.tourapi.mandi.domain.post.dto.DetailPostDto;
import com.tourapi.mandi.domain.post.dto.PostDto;
import com.tourapi.mandi.domain.post.dto.UpdatePostRequestDto;
import com.tourapi.mandi.domain.post.entity.Category;
import com.tourapi.mandi.domain.post.entity.Post;
import com.tourapi.mandi.domain.post.entity.PostImage;
import com.tourapi.mandi.domain.post.repository.PostRepository;
import com.tourapi.mandi.domain.post.util.PostMapper;
import com.tourapi.mandi.domain.user.UserExceptionStatus;
import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.domain.user.repository.UserJpaRepository;
import com.tourapi.mandi.global.exception.Exception403;
import com.tourapi.mandi.global.exception.Exception404;
import com.tourapi.mandi.global.util.S3ImageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserJpaRepository userJpaRepository;
    private final S3ImageClient s3ImageClient;

    @Transactional(readOnly = true)
    public Page<PostDto> getPostsByCategory(Category category, int page, int size) {
        // Pageable 객체 생성 (page와 size를 설정)
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage;

        // 카테고리가 'ALL'인 경우 모든 게시글을 최신순으로 가져옴
        if (category == Category.ALL) {
            postPage = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        } else {
            // 특정 카테고리로 게시글을 최신순으로 가져옴
            postPage = postRepository.findByCategoryOrderByCreatedAtDesc(category, pageable);
        }

        // Post 엔티티를 PostDto로 변환
        return postPage.map(PostMapper::toPostDto);
    }





    public PostDto createPost(User user, CreatePostRequestDto createPostRequestDto) {

        // User 검증: 사용자 존재 여부 확인
        User existingUser = userJpaRepository.findById(user.getUserId()).orElseThrow(
                () -> new Exception404(UserExceptionStatus.USER_NOT_FOUND)
        );

        //Post생성
        Post newPost=PostMapper.toPostFromCreatePostRequestDto(existingUser,createPostRequestDto,s3ImageClient);

        // Post 엔티티 저장
        postRepository.save(newPost);

        // CreatePostResponseDto 생성 및 반환
        return PostMapper.toPostDto(newPost);
    }

    @Transactional(readOnly = true)
    public DetailPostDto getPostById(Long id) {


        // Post 검증: 해당 id로 게시글 존재 여부 확인
        Post existingPost = postRepository.findPostWithDetailsById(id).orElseThrow(
                () -> new Exception404(PostExceptionStatus.POST_NOT_FOUND)
        );

        // PostDto로 변환하여 반환
        return PostMapper.toDetailPostDto(existingPost);
    }


    public boolean deletePost(Long id,User user) {

        Post existingPost = postRepository.findPostWithDetailsById(id).orElseThrow(
                () -> new Exception404(PostExceptionStatus.POST_NOT_FOUND)
        );

        // 게시글 작성자와 요청한 사용자가 일치하는지 확인
        if (!existingPost.getUser().getUserId().equals(user.getUserId())) {
            throw new Exception403(PostExceptionStatus.USER_NOT_AUTHORIZED);
        }
        
        // S3에 해당하는 이미지들 전부삭제
        existingPost.getPostImageList().forEach(postImage -> s3ImageClient.deleteImageFromS3(postImage.getUrl()));

        // Post 삭제
        postRepository.deleteById(id);

        return true;
    }



    public PostDto updatePost(Long id, User user, UpdatePostRequestDto updatePostRequestDto) {

        Post existingPost = postRepository.findPostWithDetailsById(id).orElseThrow(
                () -> new Exception404(PostExceptionStatus.POST_NOT_FOUND)
        );

        // 게시글 작성자와 요청한 사용자가 일치하는지 확인
        if (!existingPost.getUser().getUserId().equals(user.getUserId())) {
            throw new Exception403(PostExceptionStatus.USER_NOT_AUTHORIZED);
        }

        //우선 dto에잇는 정보들로 post 내용변경
        existingPost.setTitle(updatePostRequestDto.title());
        existingPost.setContent(updatePostRequestDto.content());
        existingPost.setCategory(updatePostRequestDto.category());

        //s3이미지삭제
        existingPost.getPostImageList().forEach(postImage -> s3ImageClient.deleteImageFromS3(postImage.getUrl()));

        //post에 postImage비움 양방향 관계라서 여기서 지워도 지워짐
        existingPost.getPostImageList().clear();

        // CreatePostRequestDto에서 값 추출
        List<String> base64EncodedImageList = updatePostRequestDto.Base64EncodedImageList();

        // 이미지 목록을 S3에 업로드하고 URL 리스트를 생성
        List<String> imgUrlList = base64EncodedImageList.stream()
                .map(s3ImageClient::base64ImageToS3)  // base64 값을 해독해 S3에 업로드 후 URL 반환
                .toList();

        List<PostImage> postImageList = imgUrlList.stream()
                .map(url -> PostMapper.toPostImageFromUrl(url, existingPost))
                .collect(Collectors.toList());

        // Post에 이미지 리스트를 설정
        existingPost.getPostImageList().addAll(postImageList);

        return PostMapper.toPostDto(existingPost);
    }




    // 좋아요 추가
    public boolean addLike(Long postId, User user) {
        // 게시글 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new Exception404(PostExceptionStatus.POST_NOT_FOUND));


        //유저만 좋아요 가능
        userJpaRepository.findById(user.getUserId())
                .orElseThrow(() -> new Exception404(UserExceptionStatus.USER_NOT_FOUND));

        // 좋아요 수 증가
        post.setLikeCnt(post.getLikeCnt() + 1);


        return true;
    }

    // 좋아요 삭제
    @Transactional
    public boolean removeLike(Long postId, User user) {
        // 게시글 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new Exception404(PostExceptionStatus.POST_NOT_FOUND));

        //유저만 좋아요 가능
        userJpaRepository.findById(user.getUserId())
                .orElseThrow(() -> new Exception404(UserExceptionStatus.USER_NOT_FOUND));



        // 좋아요 수 감소
        post.setLikeCnt(post.getLikeCnt() - 1);


        return true;
    }

}
