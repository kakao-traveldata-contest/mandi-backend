package com.tourapi.mandi.domain.user.util;

import com.tourapi.mandi.domain.user.dto.ProfileInfoResponseDto;
import com.tourapi.mandi.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProfileInfoMapper {

    public static ProfileInfoResponseDto toProfileInfoResponseDto(User existingUser) {
        return ProfileInfoResponseDto.builder()
                .nickname(existingUser.getNickname())
                .imgUrl(existingUser.getImgUrl())
                .description(existingUser.getDescription())
                .totalReviews(0)
                .completedCourses(0)
                .email(existingUser.getEmail())
                .provider(existingUser.getProvider())
                .build();
    }
}
