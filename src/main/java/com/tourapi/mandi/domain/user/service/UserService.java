package com.tourapi.mandi.domain.user.service;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tourapi.mandi.domain.user.UserExceptionStatus;
import com.tourapi.mandi.domain.user.dto.LoginResponseDto;
import com.tourapi.mandi.domain.user.dto.ReissueDto;
import com.tourapi.mandi.domain.user.dto.SignupRequestDto;
import com.tourapi.mandi.domain.user.dto.oauth.OauthUserInfo;
import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.domain.user.entity.constant.Role;
import com.tourapi.mandi.domain.user.repository.UserJpaRepository;
import com.tourapi.mandi.global.exception.Exception400;
import com.tourapi.mandi.global.exception.Exception404;
import com.tourapi.mandi.global.redis.RedisExceptionStatus;
import com.tourapi.mandi.global.redis.service.TokenService;
import com.tourapi.mandi.global.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userJpaRepository;
    private final TokenService tokenService;

    public LoginResponseDto socialLogin(OauthUserInfo userInfo) {
        Optional<User> userOptional = userJpaRepository.findByEmail(userInfo.email());
        //유저정보 있을경우 => 이미 가입한 유저
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            String accessToken = JwtProvider.create(user);
            String refreshToken = JwtProvider.createRefreshToken(user);

            tokenService.save(refreshToken, accessToken, user);
            return new LoginResponseDto(refreshToken,accessToken,true);

        }
        //유저정보 없을경우 => 처음 가입하는 유저
        return new LoginResponseDto(null,null,false);
    }

    public LoginResponseDto socialSignup(OauthUserInfo userInfo, SignupRequestDto signupRequestDto) {
        //유저 정보만들고
            User user = User.builder()
                    .email(userInfo.email())
                    .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                    .role(Role.ROLE_USER)
                    .provider(userInfo.provider())
                    .nickname(signupRequestDto.nickname())
                    .description(signupRequestDto.description())
                    .build();
            userJpaRepository.save(user);
        //토큰 만들어서 리턴
            String accessToken = JwtProvider.create(user);
            String refreshToken = JwtProvider.createRefreshToken(user);
            tokenService.save(refreshToken, accessToken, user);

            return new LoginResponseDto(accessToken, refreshToken, true);
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
        return ReissueDto.ReissueResponseDto.of(newAccessToken, newRefreshToken);
    }

    public void logout(String accessToken) {
        tokenService.deleteByAccessToken(accessToken);
    }

    public void withdrawal(User user, String accessToken) {
        userJpaRepository.findById(user.getUserId()).ifPresentOrElse(
                userJpaRepository::delete,
                () -> {
                    throw new Exception404(UserExceptionStatus.USER_NOT_FOUND);
                }
        );
        tokenService.deleteByAccessToken(accessToken);
    }

    public void checkRefreshTokenInRedis(String refreshToken) {
        if (!tokenService.existsById(refreshToken)) {
            throw new Exception404(RedisExceptionStatus.REFRESH_TOKEN_NOT_FOUND);
        }
    }

    public User getUserByRefreshToken(String refreshToken) {
        try {
            DecodedJWT decodedJwt = JwtProvider.verifyRefreshToken(refreshToken);
            Long id = decodedJwt.getClaim("id").asLong();

            return userJpaRepository.findById(id)
                    .orElseThrow(() -> new Exception404(UserExceptionStatus.USER_NOT_FOUND));
        } catch (SignatureVerificationException | JWTDecodeException e) {
            log.error(e.getMessage());
            throw new Exception400(UserExceptionStatus.REFRESH_TOKEN_INVALID);
        } catch (TokenExpiredException tee) {
            throw new Exception400(UserExceptionStatus.REFRESH_TOKEN_EXPIRED);
        }
    }
}
