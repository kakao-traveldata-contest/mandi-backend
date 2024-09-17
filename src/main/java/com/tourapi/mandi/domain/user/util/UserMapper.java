package com.tourapi.mandi.domain.user.util;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import com.tourapi.mandi.domain.user.dto.SignupRequestDto;
import com.tourapi.mandi.domain.user.dto.oauth.OauthUserInfo;
import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.domain.user.entity.constant.Role;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;



@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {

    public static User toUserFromSignupRequestDto(SignupRequestDto signupRequestDto, String defaultImageUrl, PasswordEncoder passwordEncoder, OauthUserInfo userInfo) {
        return User.builder()
                .email(userInfo.email())
                .password(passwordEncoder.encode(UUID.randomUUID().toString())) // 랜덤 비밀번호 생성 및 암호화
                .role(Role.ROLE_USER) // 기본 ROLE_USER 지정
                .provider(userInfo.provider())
                .nickname(signupRequestDto.nickname())
                .description(signupRequestDto.description())
                .imgUrl(defaultImageUrl)
                .build();
    }
    public static User toUserFromJwt(DecodedJWT decodedJwt) {
        String email = decodedJwt.getClaim("email").asString();
        Long id = decodedJwt.getClaim("id").asLong();
        Role role = decodedJwt.getClaim("role").as(Role.class);

        return User.builder()
                .userId(id)
                .email(email)
                .role(role)
                .build();
    }
}
