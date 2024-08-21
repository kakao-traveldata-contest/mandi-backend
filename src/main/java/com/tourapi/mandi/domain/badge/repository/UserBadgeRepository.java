package com.tourapi.mandi.domain.badge.repository;

import com.tourapi.mandi.domain.badge.entity.Badge;
import com.tourapi.mandi.domain.badge.entity.UserBadge;
import com.tourapi.mandi.domain.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {

    @Query("select ub.badge from UserBadge ub where ub.user = :user")
    List<Badge> findAllByUser(@Param("user") User user);
}
