package com.tourapi.mandi.domain.post.service;


import com.tourapi.mandi.domain.post.PostExceptionStatus;
import com.tourapi.mandi.domain.post.dto.CreatePostRequestDto;
import com.tourapi.mandi.domain.post.dto.DetailPostDto;
import com.tourapi.mandi.domain.post.dto.PostDto;
import com.tourapi.mandi.domain.post.dto.UpdatePostRequestDto;
import com.tourapi.mandi.domain.post.entity.Category;
import com.tourapi.mandi.domain.post.entity.Post;
import com.tourapi.mandi.domain.post.repository.PostRepository;
import com.tourapi.mandi.domain.post.util.PostMapper;
import com.tourapi.mandi.domain.user.UserExceptionStatus;
import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.domain.user.repository.UserJpaRepository;
import com.tourapi.mandi.global.exception.Exception404;
import com.tourapi.mandi.global.util.S3ImageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


    public boolean deletePost(Long id) {

        Post existingPost = postRepository.findPostWithDetailsById(id).orElseThrow(
                () -> new Exception404(PostExceptionStatus.POST_NOT_FOUND)
        );
        
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

        // S3에 해당하는 이미지들 전부삭제
        existingPost.getPostImageList().forEach(postImage -> s3ImageClient.deleteImageFromS3(postImage.getUrl()));

        // Post 삭제
        postRepository.deleteById(id);

        return true;
    }

}
