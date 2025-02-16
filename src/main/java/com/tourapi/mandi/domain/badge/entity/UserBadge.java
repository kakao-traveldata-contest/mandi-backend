package com.tourapi.mandi.domain.badge.entity;

import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.global.util.AuditingEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_badge_tb")
public class UserBadge extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_badge_id")
    private Long badgeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id")
    private Badge badge;

    @Builder
    public UserBadge(Badge badge, User user) {
        this.user = user;
        this.badge = badge;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UserBadge other = (UserBadge) obj;
        return Objects.equals(getBadgeId(), other.getBadgeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBadgeId());
    }
}
