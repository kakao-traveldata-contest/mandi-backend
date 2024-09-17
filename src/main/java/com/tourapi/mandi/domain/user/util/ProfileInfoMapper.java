package com.tourapi.mandi.domain.user.util;

import com.tourapi.mandi.domain.user.dto.ProfileInfoResponseDto;
import com.tourapi.mandi.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProfileInfoMapper {

    public static ProfileInfoResponseDto toProfileInfoResponseDto(User existingUser) {
        return new ProfileInfoResponseDto(
                existingUser.getNickname(),
                existingUser.getImgUrl(),
                existingUser.getDescription(),
                0,  // Placeholder for data, adjust as needed
                0,  // Placeholder for data, adjust as needed
                existingUser.getEmail(),
                existingUser.getProvider()
        );
    }
}
