package com.tourapi.mandi.domain.post.util;

import com.tourapi.mandi.domain.post.dto.PostDto;
import com.tourapi.mandi.domain.post.dto.PostImageDto;
import com.tourapi.mandi.domain.post.dto.PostsByCategoryResponseDto;
import com.tourapi.mandi.domain.post.dto.UserDto;
import com.tourapi.mandi.domain.post.entity.Post;
import com.tourapi.mandi.domain.post.entity.PostImage;
import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.global.dto.PageInfoDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PostMapper {


    public static PostDto toPostDto(Post post) {
        return PostDto.builder()
                .postId(post.getPostId())
                .user(toUserDto(post.getUser()))
                .category(post.getCategory())
                .content(post.getContent())
                .title(post.getTitle())
                .listPostImg(post.getListPostImg().stream().map(PostMapper::toPostImageDto).toList())
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

}
