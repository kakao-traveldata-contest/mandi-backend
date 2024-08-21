package com.tourapi.mandi.domain.badge.entity;


import com.tourapi.mandi.global.util.AuditingEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "badge_tb")
public class Badge extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private Long badgeId;

    @Column(length = 30, nullable = false, unique = true, name = "name")
    private String name;

    @Column(length = 100, nullable = false, name = "requirements")
    private String requirements;

    @Column(length = 512, nullable = false, name = "img_url")
    private String imgUrl;

    @Builder
    public Badge(Long badgeId, String name, String requirements, String imgUrl) {
        this.badgeId = badgeId;
        this.name = name;
        this.requirements = requirements;
        this.imgUrl = imgUrl;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Badge other = (Badge) obj;
        return Objects.equals(getBadgeId(), other.getBadgeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBadgeId());
    }
}
