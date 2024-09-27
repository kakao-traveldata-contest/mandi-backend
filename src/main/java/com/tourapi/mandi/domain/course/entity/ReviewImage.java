package com.tourapi.mandi.domain.course.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "review_image_tb")
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "completed_course_id", nullable = false)
    private CompletedCourse completedCourse;

    @Column(length = 512, nullable = false)
    private String url;

    @Builder
    public ReviewImage(Long reviewImageId, CompletedCourse completedCourse, String url) {
        this.reviewImageId = reviewImageId;
        this.completedCourse = completedCourse;
        this.url = url;
    }
}
