package com.tourapi.mandi.domain.user.util;

import com.tourapi.mandi.domain.course.entity.CoursePreference;
import com.tourapi.mandi.domain.course.entity.DifficultyLevel;
import com.tourapi.mandi.domain.course.entity.DurationLevel;
import com.tourapi.mandi.domain.course.entity.EnvironmentType;
import com.tourapi.mandi.domain.user.dto.CoursePreferenceRequestDto;
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

    public static CoursePreference toCoursePreference(final CoursePreferenceRequestDto requestDto) {
        DifficultyLevel difficultyLevel = DifficultyLevel.get(requestDto.difficultyLevel()).orElseThrow();
        DurationLevel durationLevel = DurationLevel.get(requestDto.durationLevel()).orElseThrow();
        EnvironmentType environmentType = EnvironmentType.get(requestDto.environmentLevel()).orElseThrow();

        return CoursePreference.builder()
                .difficultyLevel(difficultyLevel)
                .durationLevel(durationLevel)
                .environmentType(environmentType)
                .build();
    }
}
