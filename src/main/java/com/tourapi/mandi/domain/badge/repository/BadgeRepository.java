package com.tourapi.mandi.domain.badge.repository;

import com.tourapi.mandi.domain.badge.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
}
