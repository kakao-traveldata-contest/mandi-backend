package com.tourapi.mandi.domain.post.dto;

import com.tourapi.mandi.domain.user.entity.User;
import lombok.Builder;

@Builder
public record UserDto(
        Long userId,
        String nickname,
        String imgUrl
) {}
