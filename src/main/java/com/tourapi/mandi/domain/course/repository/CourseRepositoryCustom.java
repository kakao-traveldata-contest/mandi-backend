package com.tourapi.mandi.domain.course.repository;

import com.tourapi.mandi.domain.course.dto.CourseSearchDto;
import com.tourapi.mandi.domain.course.entity.Course;
import org.springframework.data.domain.Page;

public interface CourseRepositoryCustom {

    Page<Course> findCoursesBySearch(CourseSearchDto courseSearch);
}
