package com.tourapi.mandi.domain.user.util;

import com.tourapi.mandi.domain.user.dto.LoginResponseDto;
import com.tourapi.mandi.domain.user.dto.ReissueDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoginMapper {

    public static LoginResponseDto toLoginResponseDto(String refreshToken, String accessToken, boolean isSignUp) {
        return LoginResponseDto.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .isSignUp(isSignUp)
                .build();
    }



    public static ReissueDto.ReissueResponseDto toReissueResponseDto(String newAccessToken, String newRefreshToken) {
        return ReissueDto.ReissueResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
