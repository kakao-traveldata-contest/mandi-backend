package com.tourapi.mandi.domain.comment.controller;


import com.tourapi.mandi.domain.comment.service.CommentService;
import com.tourapi.mandi.domain.post.dto.PostDto;
import com.tourapi.mandi.domain.post.dto.PostsByCategoryResponseDto;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "댓글 API 목록")
@RequiredArgsConstructor
@RequestMapping("/comments")
@RestController
public class CommentController {



    private final CommentService commentService;


    @Operation(summary = "댓글 좋아요 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 좋아요 추가 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 댓글에 좋아요 추가 에러"),
            @ApiResponse(responseCode = "404", description = "유저가 아닐경우 좋아요 추가 에러")
    })
    @PostMapping("/{id}/like")
    public ResponseEntity<ApiUtils.ApiResult<Boolean>> addLike(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id) {

        // 좋아요 추가 로직 실행
        boolean isLiked = commentService.addLike(id, userDetails.user());

        // 좋아요 추가 결과 반환
        return ResponseEntity.ok(ApiUtils.success(isLiked));
    }



    @Operation(summary = "댓글 좋아요 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 좋아요 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 댓글에 좋아요 삭제 에러"),
            @ApiResponse(responseCode = "404", description = "유저가 아닐경우 좋아요 삭제 에러")
    })
    @DeleteMapping("/{id}/like")
    public ResponseEntity<ApiUtils.ApiResult<Boolean>> removeLike(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id) {

        // 좋아요 추가 로직 실행
        boolean isLiked = commentService.removeLike(id, userDetails.user());

        // 좋아요 추가 결과 반환
        return ResponseEntity.ok(ApiUtils.success(isLiked));
    }

}
