package com.tourapi.mandi.domain.user.util;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.tourapi.mandi.domain.course.entity.CoursePreference;
import com.tourapi.mandi.domain.course.entity.DifficultyLevel;
import com.tourapi.mandi.domain.course.entity.DurationLevel;
import com.tourapi.mandi.domain.course.entity.EnvironmentType;
import com.tourapi.mandi.domain.post.dto.UserDto;
import com.tourapi.mandi.domain.user.dto.SignupRequestDto;
import com.tourapi.mandi.domain.user.dto.oauth.OauthUserInfo;
import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.domain.user.entity.constant.Role;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;



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

    public static CoursePreference toCoursePreference(final SignupRequestDto requestDto) {
        DifficultyLevel difficultyLevel = DifficultyLevel.get(requestDto.difficultyLevel()).orElseThrow();
        DurationLevel durationLevel = DurationLevel.get(requestDto.durationLevel()).orElseThrow();
        EnvironmentType environmentType = EnvironmentType.get(requestDto.environmentLevel()).orElseThrow();

        return CoursePreference.builder()
                .difficultyLevel(difficultyLevel)
                .durationLevel(durationLevel)
                .environmentType(environmentType)
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

    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .imgUrl(user.getImgUrl())
                .build();
    }
}
