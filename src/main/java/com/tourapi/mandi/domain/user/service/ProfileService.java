package com.tourapi.mandi.domain.user.service;

import com.tourapi.mandi.domain.user.UserExceptionStatus;
import com.tourapi.mandi.domain.user.dto.ProfileImageChangeRequestDto;
import com.tourapi.mandi.domain.user.dto.ProfileInfoResponseDto;
import com.tourapi.mandi.domain.user.dto.ProfileUpdateRequestDto;
import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.domain.user.repository.UserJpaRepository;
import com.tourapi.mandi.domain.user.util.ProfileInfoMapper;
import com.tourapi.mandi.global.exception.Exception409;
import com.tourapi.mandi.global.util.S3ImageClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ProfileService {
    private final UserService userService;
    private final UserJpaRepository userJpaRepository;
    private final S3ImageClient s3ImageClient;

    @Transactional(readOnly = true)
    public boolean checkNicknameDuplication(String nickname) {
        // 중복된 닉네임인 경우 409 상태코드를 반환한다.
        // * 409 (conflict): 대상 리소스가 현재 상태와 충돌하여 요청을 완료할 수 없음을 나타내는 코드
        if (userJpaRepository.existsByNickname(nickname)) {
            throw new Exception409(UserExceptionStatus.NICKNAME_ALREADY_EXISTS);
        }
        return true;
    }

    public Boolean updateProfile(ProfileUpdateRequestDto requestDto, User user) {
        User existingUser = userService.getExistingUser(user);

        if (!requestDto.nickname().equals(existingUser.getNickname())) {
            checkNicknameDuplication(requestDto.nickname());
            existingUser.setNickname(requestDto.nickname());
        }

        if (!requestDto.description().equals(existingUser.getDescription())) {
            existingUser.setDescription(requestDto.description());
        }

        return true;
    }

    public String changeProfileImage(ProfileImageChangeRequestDto requestDto, User user) {

        // 유저 정보를 이메일로 조회 (혹은 다른 방식으로 조회)
        User existingUser = userService.getExistingUser(user);

        // 기존 이미지 URL이 존재하면 삭제
        if (existingUser.getImgUrl() != null && !existingUser.getImgUrl().isEmpty()) {
            s3ImageClient.deleteImageFromS3(existingUser.getImgUrl());
        }

        // 새로운 이미지 업로드 후 URL을 반환받음
        String profileImageUrl = s3ImageClient.base64ImageToS3(requestDto.Base64EncodedImage());

        // 유저 정보 업데이트
        existingUser.setImgUrl(profileImageUrl); // imgUrl 업데이트

        // 변경된 이미지 URL 반환
        return profileImageUrl;
    }

    @Transactional(readOnly = true)
    public ProfileInfoResponseDto getProfileInfo(User user) {

        // 유저 정보를 이메일로 조회
        User existingUser = userService.getExistingUser(user);

        //유저 정보를 이용해서 원하는 DTO 반환
        return ProfileInfoMapper.toProfileInfoResponseDto(existingUser);

    }
}
