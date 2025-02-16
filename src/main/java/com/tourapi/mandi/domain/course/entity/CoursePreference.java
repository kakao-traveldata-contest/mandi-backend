package com.tourapi.mandi.domain.course.entity;

import com.tourapi.mandi.global.util.AuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "course_preference_tb")
public class CoursePreference extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_preference_id")
    private Long coursePreferenceId;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private DifficultyLevel difficultyLevel;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private DurationLevel durationLevel;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private EnvironmentType environmentType;

    @Builder
    public CoursePreference(
            final DifficultyLevel difficultyLevel,
            final DurationLevel durationLevel,
            final EnvironmentType environmentType
    ) {
        this.difficultyLevel = difficultyLevel;
        this.durationLevel = durationLevel;
        this.environmentType = environmentType;
    }
}
