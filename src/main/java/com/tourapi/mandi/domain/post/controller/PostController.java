package com.tourapi.mandi.domain.post.controller;

import com.tourapi.mandi.domain.post.dto.*;
import com.tourapi.mandi.domain.post.entity.Category;
import com.tourapi.mandi.domain.post.service.PostService;
import com.tourapi.mandi.domain.post.util.PostMapper;
import com.tourapi.mandi.global.exception.Exception400;
import com.tourapi.mandi.global.security.CustomUserDetails;
import com.tourapi.mandi.global.util.ApiUtils;
import com.tourapi.mandi.global.util.UtilExceptionStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시글 API 목록")
@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController {


    private final PostService postService;

    @Operation(summary = "카테고리별 게시글 목록 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "탐색 페이지를 1보다 작게 설정했을시"),
    })
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiUtils.ApiResult<PostsByCategoryResponseDto>> getPostsByCategory(
            @PathVariable Category category,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        // 페이지가 1보다 작으면 에러 발생
        if (page < 1) {
            throw new Exception400(UtilExceptionStatus.INVALID_PAGE_NUMBER);
        }

        Page<PostDto> postPage = postService.getPostsByCategory(category, page-1, size);

        PostsByCategoryResponseDto postsByCategoryResponseD= PostMapper.toPostsByCategoryResponseDto(postPage);

        return ResponseEntity.ok(ApiUtils.success(postsByCategoryResponseD));
    }


    @Operation(summary = "게시글 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 생성 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자 요청 에러"),
            @ApiResponse(responseCode = "400", description = "S3 이미지 생성 에러")
    })
    @PostMapping("/create")
    public ResponseEntity<ApiUtils.ApiResult<PostDto>> createPost(
            @RequestBody @Valid CreatePostRequestDto createPostRequestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails)
    {

        // 게시글 생성 로직 실행
        PostDto createdPost = postService.createPost(userDetails.user(), createPostRequestDto);

        // 생성된 게시글의 정보 반환
        return ResponseEntity.ok(ApiUtils.success(createdPost));
    }



    @Operation(summary = "게시글 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글 삭제 에러"),
            @ApiResponse(responseCode = "500", description = "S3 이미지 삭제 에러")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiUtils.ApiResult<Boolean>> deletePost(
            @AuthenticationPrincipal CustomUserDetails userDetails,

            @PathVariable Long id) {

        return ResponseEntity.ok(ApiUtils.success(postService.deletePost(id)));
    }



    @Operation(summary = "게시글 변경")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 변경 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글 변경 에러"),
            @ApiResponse(responseCode = "400", description = "S3 이미지 생성 에러"),
            @ApiResponse(responseCode = "500", description = "S3 이미지 삭제 에러")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiUtils.ApiResult<PostDto>> updatePost(
            UpdatePostRequestDto updatePostRequestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id) {

        return ResponseEntity.ok(ApiUtils.success(postService.updatePost(id,userDetails.user(),updatePostRequestDto)));
    }






    @Operation(summary = "특정 게시글 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글 조희 에러")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiUtils.ApiResult<DetailPostDto>> getPost(@PathVariable Long id)
    {

        // 게시글 생성 로직 실행
        DetailPostDto detailPost = postService.getPostById(id);

        // 생성된 게시글의 정보 반환
        return ResponseEntity.ok(ApiUtils.success(detailPost));
    }


}
