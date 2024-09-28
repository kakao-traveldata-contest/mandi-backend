package com.tourapi.mandi.domain.course.entity;

import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.global.util.AuditingEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "completed_course_tb")
public class CompletedCourse extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long completedCourseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false, precision = 6, scale = 3)
    private BigDecimal distance;

    @Column(length = 512, nullable = false)
    private String trekkingPathImageUrl;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false)
    private LocalDateTime startedAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false)
    private LocalDateTime completedAt;

    @Column(nullable = false)
    private Boolean isReviewed;

    @Column(length = 700)
    private String reviewContent;

    private Integer reviewScore;

    @OneToMany(mappedBy = "completedCourse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImage> reviewImageList;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime reviewedAt;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        CompletedCourse other = (CompletedCourse) obj;
        return Objects.equals(getCompletedCourseId(), other.getCompletedCourseId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCompletedCourseId());
    }

    public void addReview(
            String reviewContent,
            Integer reviewScore,
            List<ReviewImage> reviewImages
    ) {
        this.isReviewed = true;
        updateReview(reviewContent, reviewScore, reviewImages);
    }

    private void updateReview(String reviewContent, Integer reviewScore, List<ReviewImage> reviewImages) {
        Optional.ofNullable(reviewScore).ifPresent(score -> this.reviewScore = score);
        Optional.ofNullable(reviewContent).ifPresent(contents -> this.reviewContent = contents);
        Optional.ofNullable(reviewImages).ifPresent(images -> this.reviewImageList.addAll(images));
        this.reviewedAt = LocalDateTime.now();
    }
}
