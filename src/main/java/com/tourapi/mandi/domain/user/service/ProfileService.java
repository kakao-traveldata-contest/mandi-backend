package com.tourapi.mandi.domain.user.service;

import com.tourapi.mandi.domain.user.UserExceptionStatus;
import com.tourapi.mandi.domain.user.dto.ProfileUpdateRequestDto;
import com.tourapi.mandi.domain.user.dto.UserProfileDto;
import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.domain.user.repository.UserJpaRepository;
import com.tourapi.mandi.global.exception.Exception409;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProfileService {
    private final UserService userService;
    private final UserJpaRepository userJpaRepository;

    public boolean checkNicknameDuplication(String nickname) {
        // 중복된 닉네임인 경우 409 상태코드를 반환한다.
        // * 409 (conflict): 대상 리소스가 현재 상태와 충돌하여 요청을 완료할 수 없음을 나타내는 코드
        if (userJpaRepository.existsByNickname(nickname)) {
            throw new Exception409(UserExceptionStatus.NICKNAME_ALREADY_EXISTS);
        }
        return true;
    }

    @Transactional
    public void updateProfile(ProfileUpdateRequestDto requestDto) {
        userService.checkRefreshTokenInRedis(requestDto.refreshToken());

        User user = userService.getUserByRefreshToken(requestDto.refreshToken());

        if (requestDto.nickname() != null && !requestDto.nickname().equals(user.getNickname())) {
            checkNicknameDuplication(requestDto.nickname());

            user.setNickname(requestDto.nickname());
        }

        if(requestDto.description() != null && !requestDto.description().equals(user.getDescription())) {
            user.setDescription(requestDto.description());
        }
    }

    public UserProfileDto getUserProfile(String refreshToken) {
        User user = userService.getUserByRefreshToken(refreshToken);

        return new UserProfileDto(
                user.getNickname(),
                user.getDescription(),
                user.getImgUrl()
        );
    }
}
