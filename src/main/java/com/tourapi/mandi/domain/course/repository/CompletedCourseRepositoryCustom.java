package com.tourapi.mandi.domain.course.repository;

import com.tourapi.mandi.domain.course.dto.CourseReviewSearch;
import com.tourapi.mandi.domain.course.entity.CompletedCourse;
import org.springframework.data.domain.Page;

public interface CompletedCourseRepositoryCustom {
    Page<CompletedCourse> findReviewsBySearch(Long courseId, CourseReviewSearch courseReviewSearch);
    ReviewSummary getReviewSummary(Long courseId);
}
