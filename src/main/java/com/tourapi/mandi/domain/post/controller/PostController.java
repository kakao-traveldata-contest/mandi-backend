package com.tourapi.mandi.domain.post.controller;

import com.tourapi.mandi.domain.badge.dto.BadgeListResponseDto;
import com.tourapi.mandi.domain.badge.service.BadgeService;
import com.tourapi.mandi.domain.post.dto.PostsByCategoryResponseDto;
import com.tourapi.mandi.domain.post.entity.Category;
import com.tourapi.mandi.domain.post.entity.Post;
import com.tourapi.mandi.domain.post.service.PostService;
import com.tourapi.mandi.global.dto.PageInfoDto;
import com.tourapi.mandi.global.security.CustomUserDetails;
import com.tourapi.mandi.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Operation(summary = "내가 쓴 게시글 목록 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공"),
    })
    @GetMapping("/category/{category}")
    public PostsByCategoryResponseDto getPostsByCategory(@PathVariable Category category,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        Page<Post> postPage = postService.getPostsByCategory(category, page, size);
        PageInfoDto pageInfo = new PageInfoDto(postPage);
        return new PostsByCategoryResponseDto(postPage.getContent(), pageInfo);
    }

}
