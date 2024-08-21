package com.tourapi.mandi.domain.badge.service;

import com.tourapi.mandi.domain.badge.dto.BadgeListResponseDto;
import com.tourapi.mandi.domain.badge.dto.BadgeResponseDto;
import com.tourapi.mandi.domain.badge.entity.Badge;
import com.tourapi.mandi.domain.badge.repository.BadgeRepository;
import com.tourapi.mandi.domain.badge.repository.UserBadgeRepository;
import com.tourapi.mandi.domain.badge.util.BadgeMapper;
import com.tourapi.mandi.domain.user.UserExceptionStatus;
import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.domain.user.repository.UserJpaRepository;
import com.tourapi.mandi.global.exception.Exception404;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

;

@Service
@RequiredArgsConstructor
@Slf4j
public class BadgeService {

    private final UserJpaRepository userJpaRepository;
    private final BadgeRepository badgeRepository;
    private final UserBadgeRepository userBadgeRepository;

    public BadgeListResponseDto getUserBadges(Long userId) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new Exception404(UserExceptionStatus.USER_NOT_FOUND));

        List<Badge> allBadges = badgeRepository.findAll();
        List<Badge> userBadges = userBadgeRepository.findAllByUser(user);
        List<BadgeResponseDto> badges = new ArrayList<>();

        for (final Badge badge : allBadges) {
            badges.add(BadgeMapper.toBadgeResponseDto(badge, userBadges.contains(badge)));
        }
        return new BadgeListResponseDto(allBadges.size(), userBadges.size(), badges);
    }
}
