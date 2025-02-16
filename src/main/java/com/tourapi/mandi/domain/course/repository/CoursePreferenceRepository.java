package com.tourapi.mandi.domain.course.repository;

import com.tourapi.mandi.domain.course.entity.CoursePreference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursePreferenceRepository extends JpaRepository<CoursePreference, Long> {
}
