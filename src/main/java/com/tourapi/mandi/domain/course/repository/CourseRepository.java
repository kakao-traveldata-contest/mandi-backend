package com.tourapi.mandi.domain.course.repository;

import com.tourapi.mandi.domain.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long>, CourseRepositoryCustom {

}
