package com.tourapi.mandi.domain.user.service;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tourapi.mandi.domain.badge.entity.BadgeType;
import com.tourapi.mandi.domain.badge.service.BadgeService;
import com.tourapi.mandi.domain.course.entity.CoursePreference;
import com.tourapi.mandi.domain.course.repository.CoursePreferenceRepository;
import com.tourapi.mandi.domain.user.UserExceptionStatus;
import com.tourapi.mandi.domain.user.dto.LoginResponseDto;
import com.tourapi.mandi.domain.user.dto.ReissueDto;
import com.tourapi.mandi.domain.user.dto.SignupRequestDto;
import com.tourapi.mandi.domain.user.dto.oauth.OauthUserInfo;
import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.domain.user.repository.UserJpaRepository;

import com.tourapi.mandi.domain.user.util.LoginMapper;
import com.tourapi.mandi.domain.user.util.UserMapper;
import com.tourapi.mandi.global.exception.Exception400;
import com.tourapi.mandi.global.exception.Exception404;
import com.tourapi.mandi.global.redis.RedisExceptionStatus;
import com.tourapi.mandi.global.redis.service.BlackListTokenService;
import com.tourapi.mandi.global.redis.service.TokenService;
import com.tourapi.mandi.global.security.JwtProvider;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class UserService {


    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userJpaRepository;
    private final TokenService tokenService;
    private final BlackListTokenService blackListTokenService;
    private final BadgeService badgeService;
    private final CoursePreferenceRepository preferenceRepository;

    private final String defaultImageUrl ="https://mandi-image.s3.ap-northeast-2.amazonaws.com/image/default.png";

//해당 메서드가 호출된다는거 자체가, refreshtoken까지 만료되었거나, 아예 기존  토큰들이 프론트의 로컬스토리지에서 다지워서 새로 받아야 하거나,
    //따라서 이메서드에서가 호출되면 => 기존 redis에있는 키들을 지우고, 아예 새로만들어줘야한다.
    public LoginResponseDto socialLogin(OauthUserInfo userInfo) {
        Optional<User> userOptional = userJpaRepository.findByEmail(userInfo.email());
        //유저정보 있을경우 => 이미 가입한 유저
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String refreshToken = JwtProvider.createRefreshToken(user);
            String accessToken = JwtProvider.create(user);

            tokenService.save(refreshToken, accessToken, user);
            return LoginMapper.toLoginResponseDto(refreshToken,accessToken,true);

        }
        else{
            throw new Exception404(UserExceptionStatus.USER_NOT_FOUND);
        }
//        //유저정보 없을경우 => 처음 가입하는 유저
//        return LoginMapper.toLoginResponseDto(null,null,false);
    }

    public LoginResponseDto socialSignup(OauthUserInfo userInfo, SignupRequestDto signupRequestDto) {
        //유저 정보만들고
        User user = UserMapper.toUserFromSignupRequestDto(signupRequestDto, defaultImageUrl, passwordEncoder, userInfo);
        userJpaRepository.save(user);

        // 코스 선호도 정보 만들고
        CoursePreference coursePreference = UserMapper.toCoursePreference(signupRequestDto);
        preferenceRepository.save(coursePreference);

        // 코스 선호도 정보 저장
        user.updatePreference(coursePreference);

        //토큰 만들어서 리턴
        String refreshToken = JwtProvider.createRefreshToken(user);
        String accessToken = JwtProvider.create(user);

        tokenService.save(refreshToken, accessToken, user);
        badgeService.saveBadge(BadgeType.MANDI_STARTER, user);
        return LoginMapper.toLoginResponseDto(refreshToken,accessToken,true);
    }

    public ReissueDto.ReissueResponseDto reissue(ReissueDto.ReissueRequestDto requestDto) {
        String refreshToken = requestDto.refreshToken();

        // refresh 토큰으로 User정보 추출
        User user = getUserByRefreshToken(refreshToken);

        // refresh 토큰이 유효한지 확인
        checkRefreshTokenInRedis(refreshToken);

        String newAccessToken = JwtProvider.create(user);
        String newRefreshToken = JwtProvider.createRefreshToken(user);
        tokenService.deleteById(refreshToken);
        tokenService.save(newRefreshToken, newAccessToken, user);
        return LoginMapper.toReissueResponseDto(newAccessToken, newRefreshToken);

    }

    public void logout(String accessToken) {
        tokenService.deleteByAccessToken(accessToken);
        blackListTokenService.save(accessToken);
    }

    public boolean withdrawal(User user, String accessToken) {


        userJpaRepository.findById(user.getUserId()).ifPresentOrElse(
                userJpaRepository::delete,
                () -> {
                    throw new Exception404(UserExceptionStatus.USER_NOT_FOUND);
                }
        );
        tokenService.deleteByAccessToken(accessToken);
        blackListTokenService.save(accessToken);
        return true;
    }
    @Transactional(readOnly = true)
    public void checkRefreshTokenInRedis(String refreshToken) {
        if (!tokenService.existsById(refreshToken)) {
            throw new Exception404(RedisExceptionStatus.REFRESH_TOKEN_NOT_FOUND);
        }
    }
    @Transactional(readOnly = true)
    public User getExistingUser(User user) {
        return userJpaRepository.findById(user.getUserId())
                .orElseThrow(() -> new Exception404(UserExceptionStatus.USER_NOT_FOUND));
    }

    private User getUserByRefreshToken(String refreshToken) {
        try {
            DecodedJWT decodedJwt = JwtProvider.verifyRefreshToken(refreshToken);
            return UserMapper.toUserFromJwt(decodedJwt);
        } catch (SignatureVerificationException | JWTDecodeException e) {
            log.error(e.getMessage());
            throw new Exception400(UserExceptionStatus.REFRESH_TOKEN_INVALID);
        } catch (TokenExpiredException tee) {
            throw new Exception400(UserExceptionStatus.REFRESH_TOKEN_EXPIRED);
        }
    }





    public User getId(User user) {
        return userJpaRepository.findById(user.getUserId())
                .orElseThrow(() -> new Exception404(UserExceptionStatus.USER_NOT_FOUND));

    }
}
